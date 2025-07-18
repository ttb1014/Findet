package ru.ttb220.expense.presentation.viewmodel

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
import ru.ttb220.designsystem.DynamicIconResource
import ru.ttb220.expense.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.expense.domain.GetTransactionsForActiveAccountPeriodUseCase
import ru.ttb220.expense.presentation.model.ExpenseAnalysisItemData
import ru.ttb220.expense.presentation.model.ExpensesAnalysisScreenState
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.AnalysisData
import ru.ttb220.presentation.model.util.CurrencyMapper
import ru.ttb220.presentation.model.util.DomainErrorMessageMapper
import ru.ttb220.presentation.model.util.EmojiMapper
import ru.ttb220.presentation.model.util.NumberToStringMapper
import ru.ttb220.presentation.model.util.TimeMapper
import javax.inject.Inject

class ExpensesAnalysisScreenViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val transactionsRepository: TransactionsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val getTransactionsForActiveAccountPeriodUseCase: GetTransactionsForActiveAccountPeriodUseCase,
    private val timeProvider: TimeProvider,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
    private val timeZone: TimeZone,
) : ViewModel() {

    private val _state =
        MutableStateFlow<ExpensesAnalysisScreenState>(ExpensesAnalysisScreenState.Loading)
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
                categoriesRepository.getAllExpenseCategories(),
                getActiveAccountCurrencyUseCase()
            ) { startMonthLong, endMonthLong, categoriesResult, currencyResult ->
                val transactionsResult = getTransactionsForActiveAccountPeriodUseCase(
                    false,
                    startMonthLong.asLocalDate(),
                    endMonthLong.asLocalDate(),
                ).first()

                if (transactionsResult is SafeResult.Failure) {
                    _state.value = ExpensesAnalysisScreenState.ErrorResource(
                        DomainErrorMessageMapper.toMessageRes(transactionsResult.cause)
                    )
                    return@combine
                }
                if (currencyResult is SafeResult.Failure) {
                    _state.value = ExpensesAnalysisScreenState.ErrorResource(
                        DomainErrorMessageMapper.toMessageRes(currencyResult.cause)
                    )
                    return@combine
                }
                if (categoriesResult is SafeResult.Failure) {
                    _state.value = ExpensesAnalysisScreenState.ErrorResource(
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

                val analysedExpenses = categories.mapNotNull { category ->
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
                        NumberToStringMapper.mapPercentage(totalAmountDouble / totalApplicableDouble)

                    AnalysisData(
                        emojiData = EmojiMapper.getEmojiData(category.emoji),
                        name = category.name,
                        description = description,
                        percentage = percentage,
                        amount = totalApplicableStringWithCurrencySymbol
                    )
                }

                if (analysedExpenses.isEmpty()) {
                    _state.value = ExpensesAnalysisScreenState.Empty(
                        beginMonthWithYear = startMonthLong.asMonthWithYear(),
                        endMonthWithYear = endMonthLong.asMonthWithYear(),
                        totalAmount = totalStringWithCurrencySymbol
                    )
                    return@combine
                }

                _state.value = ExpensesAnalysisScreenState.Content(
                    beginMonthWithYear = startMonthLong.asMonthWithYear(),
                    endMonthWithYear = endMonthLong.asMonthWithYear(),
                    totalAmount = totalStringWithCurrencySymbol,
                    items = analysedExpenses.map { it.toExpenseAnalysisItemData() }
                )
            }.collect { }
        }
    }

    fun Long.asLocalDate(): LocalDate {
        return Instant.fromEpochMilliseconds(this)
            .toLocalDateTime(timeZone)
            .date
    }

    private fun AnalysisData.toExpenseAnalysisItemData(): ExpenseAnalysisItemData =
        ExpenseAnalysisItemData(
            leadingIcon = run {
                emojiData?.let {
                    DynamicIconResource.EmojiIconResource(it)
                } ?: DynamicIconResource.TextIconResource(
                    name
                        .split(" ")
                        .joinToString("")
                        { (it.firstOrNull()?.lowercase() ?: " ") })
            },
            expenseName = name,
            expenseDescription = description,
            expensePercentage = percentage,
            expenseAmount = amount
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
