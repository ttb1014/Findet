package ru.ttb220.income.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.CategoriesRepository
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.designsystem.component.DynamicIconResource
import ru.ttb220.income.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.income.domain.GetTransactionsForActiveAccountPeriodUseCase
import ru.ttb220.income.domain.MakeCircleDiagramDataUseCase
import ru.ttb220.income.presentation.model.IncomeAnalysisItemData
import ru.ttb220.income.presentation.model.IncomesAnalysisScreenState
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.AnalysisData
import ru.ttb220.presentation.model.screen.AnalysisScreenData
import ru.ttb220.presentation.model.util.CurrencyMapper
import ru.ttb220.presentation.model.util.DomainErrorMessageMapper
import ru.ttb220.presentation.model.util.EmojiMapper
import ru.ttb220.presentation.model.util.NumberToStringMapper
import ru.ttb220.presentation.model.util.TimeMapper
import javax.inject.Inject

class IncomesAnalysisScreenViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val transactionsRepository: TransactionsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val getTransactionsForActiveAccountPeriodUseCase: GetTransactionsForActiveAccountPeriodUseCase,
    private val makeCircleDiagramDataUseCase: MakeCircleDiagramDataUseCase,
    private val timeProvider: TimeProvider,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
    private val timeZone: TimeZone,
) : ViewModel() {

    private val _state =
        MutableStateFlow<IncomesAnalysisScreenState>(IncomesAnalysisScreenState.Loading)
    val state = _state.asStateFlow()

    private var startMonthLong = MutableStateFlow<Long>(0L)
    private var endMonthLong = MutableStateFlow<Long>(0L)

    fun setStartDate(long: Long?) {
        if (long == null)
            return

        startMonthLong.value = long
    }

    fun setEndDate(long: Long?) {
        if (long == null)
            return

        endMonthLong.value = long
    }

    init {
        startMonthLong.value =
            timeProvider.startOfAMonth().atStartOfDayIn(timeZone).toEpochMilliseconds()
        endMonthLong.value =
            timeProvider.endOfAMonth().atStartOfDayIn(timeZone).toEpochMilliseconds()

        viewModelScope.launch {
            combine(
                startMonthLong,
                endMonthLong,
                categoriesRepository.getAllIncomeCategories(),
                getActiveAccountCurrencyUseCase()
            ) { startMonthLong, endMonthLong, categoriesResult, currencyResult ->
                val transactionsResult = getTransactionsForActiveAccountPeriodUseCase(
                    true,
                    startMonthLong.asLocalDate(),
                    endMonthLong.asLocalDate(),
                ).first()

                if (transactionsResult is SafeResult.Failure) {
                    _state.value = IncomesAnalysisScreenState.ErrorResource(
                        DomainErrorMessageMapper.toMessageRes(transactionsResult.cause)
                    )
                    return@combine
                }
                if (currencyResult is SafeResult.Failure) {
                    _state.value = IncomesAnalysisScreenState.ErrorResource(
                        DomainErrorMessageMapper.toMessageRes(currencyResult.cause)
                    )
                    return@combine
                }
                if (categoriesResult is SafeResult.Failure) {
                    _state.value = IncomesAnalysisScreenState.ErrorResource(
                        DomainErrorMessageMapper.toMessageRes(categoriesResult.cause)
                    )
                    return@combine
                }

                val transactions = (transactionsResult as SafeResult.Success).data
                val categories = (categoriesResult as SafeResult.Success).data
                val currencyCode = (currencyResult as SafeResult.Success).data
                val totalAmountDouble = transactions.fold(0.0) { acc, transaction ->
                    acc + transaction.amount
                }
                val totalStringWithCurrencySymbol = NumberToStringMapper.map(
                    totalAmountDouble,
                    CurrencyMapper.getSymbol(currencyCode)
                )

                val analysedIncomes = categories.mapNotNull { category ->
                    val applicableTransactions =
                        transactions.filter { it.category.id == category.id }
                    val description =
                        applicableTransactions.firstOrNull { !it.comment.isNullOrBlank() }?.comment
                    val totalApplicableDouble =
                        applicableTransactions.fold(0.0) { acc, transaction ->
                            acc + transaction.amount
                        }
                    if (totalApplicableDouble == 0.0) {
                        return@mapNotNull null
                    }
                    val totalApplicableStringWithCurrencySymbol = NumberToStringMapper.map(
                        totalApplicableDouble,
                        CurrencyMapper.getSymbol(currencyCode)
                    )
                    val percentage =
                        NumberToStringMapper.mapPercentage(totalApplicableDouble/ totalAmountDouble)

                    AnalysisData(
                        emojiData = EmojiMapper.getEmojiData(category.emoji),
                        name = category.name,
                        description = description,
                        percentage = percentage,
                        amount = totalApplicableStringWithCurrencySymbol
                    )
                }

                AnalysisScreenData(
                    startDate = startMonthLong.asMonthWithYear(),
                    endDate = endMonthLong.asMonthWithYear(),
                    amount = totalStringWithCurrencySymbol,
                    analysed = analysedIncomes
                )

                if (analysedIncomes.isEmpty()) {
                    _state.value = IncomesAnalysisScreenState.Empty(
                        beginMonthWithYear = startMonthLong.asMonthWithYear(),
                        endMonthWithYear = endMonthLong.asMonthWithYear(),
                        totalStringWithCurrencySymbol
                    )
                    return@combine
                }

                _state.value = IncomesAnalysisScreenState.Content(
                    beginMonthWithYear = startMonthLong.asMonthWithYear(),
                    endMonthWithYear = endMonthLong.asMonthWithYear(),
                    totalAmount = totalStringWithCurrencySymbol,
                    items = analysedIncomes.map { it.toIncomeAnalysisItemData() },
                    circleDiagramData = makeCircleDiagramDataUseCase(
                        transactions,
                        categories
                    ),
                )
            }.collect { }
        }
    }

    fun Long.asLocalDate(): LocalDate {
        return Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(timeZone)
            .date
    }

    private fun AnalysisData.toIncomeAnalysisItemData(): IncomeAnalysisItemData =
        IncomeAnalysisItemData(
            leadingIcon = run {
                emojiData?.let {
                    DynamicIconResource.EmojiIconResource(it)
                } ?: DynamicIconResource.TextIconResource(
                    name
                        .split(" ")
                        .joinToString("")
                        { (it.firstOrNull()?.lowercase() ?: " ") })
            },
            incomeName = name,
            incomeDescription = description,
            incomePercentage = percentage,
            incomeAmount = amount
        )

    private fun Long.asMonthWithYear(): String {
        val instant = Instant.fromEpochMilliseconds(this)
        val localDate = instant.toLocalDateTime(timeZone).date
        val month = with(TimeMapper) {
            localDate.month.getDisplayName()
        }
        return "$month ${localDate.year}"
    }
}
