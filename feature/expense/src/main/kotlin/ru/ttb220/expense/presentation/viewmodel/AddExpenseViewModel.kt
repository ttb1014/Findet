package ru.ttb220.expense.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.CategoriesRepository
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.expense.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.expense.presentation.model.EditExpenseIntent
import ru.ttb220.expense.presentation.model.EditExpenseState
import ru.ttb220.expense.presentation.model.ExpenseScreenData
import ru.ttb220.expense.presentation.model.toTransactionBrief
import ru.ttb220.model.Category
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.presentation.model.util.CurrencyMapper
import javax.inject.Inject

class AddExpenseViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
    private val timeProvider: TimeProvider,
    private val timeZone: TimeZone,
) : ViewModel() {

    private val _screenState = MutableStateFlow<EditExpenseState>(EditExpenseState.Loading)
    val screenState = _screenState.asStateFlow()

    private fun addExpenseJob() = viewModelScope.launch {
        val transaction =
            (_screenState.value as EditExpenseState.Content).data.toTransactionBrief()

        transactionsRepository.createNewTransaction(transaction).collect { }
    }

    fun showDatePicker() {
        val oldValue = (_screenState.value as EditExpenseState.Content).data
        _screenState.value = EditExpenseState.Content(
            oldValue.copy(isDatePickerShown = true)
        )
    }

    fun hideDatePicker() {
        val oldValue = (_screenState.value as EditExpenseState.Content).data
        _screenState.value = EditExpenseState.Content(
            oldValue.copy(isDatePickerShown = false)
        )
    }

    fun onAmountChange(newAmount: String) {
        val oldValue = (_screenState.value as EditExpenseState.Content).data

        _screenState.value = EditExpenseState.Content(
            oldValue.copy(amount = newAmount)
        )
    }

    fun onTimeChange(newTime: String) {
        val oldValue = (_screenState.value as EditExpenseState.Content).data
        _screenState.value = EditExpenseState.Content(
            oldValue.copy(time = newTime)
        )
    }

    fun onCommentChange(newComment: String) {
        val oldValue = (_screenState.value as EditExpenseState.Content).data
        _screenState.value = EditExpenseState.Content(
            oldValue.copy(comment = newComment)
        )
    }

    fun onDateChange(newDateMillis: Long?) {
        if (newDateMillis == null) return

        val newDate = newDateMillis.asLocalDateAtTimeZone(timeZone)

        val oldValue = (_screenState.value as EditExpenseState.Content).data
        _screenState.value = EditExpenseState.Content(
            oldValue.copy(
                date = newDate.toString(),
                dateMillis = newDateMillis
            )
        )
    }

    fun onIntent(intent: EditExpenseIntent) {
        when (intent) {
            is EditExpenseIntent.ChangeAmount -> onAmountChange(intent.amount)
            is EditExpenseIntent.ChangeComment -> onCommentChange(intent.comment)
            is EditExpenseIntent.ChangeTime -> onTimeChange(intent.time)
            EditExpenseIntent.ShowDatePicker -> showDatePicker()
            is EditExpenseIntent.ChangeDate -> onDateChange(intent.date)
            EditExpenseIntent.HideDatePicker -> hideDatePicker()
        }
    }

    fun onAccountChange(
        newName: String,
        newId: Int
    ) {
        val oldValue = (_screenState.value as EditExpenseState.Content).data
        _screenState.value = EditExpenseState.Content(
            oldValue.copy(
                accountName = newName,
                accountId = newId
            )
        )
    }

    fun onCategoryChange(newName: String, newId: Int) {
        val oldValue = (_screenState.value as EditExpenseState.Content).data
        _screenState.value = EditExpenseState.Content(
            oldValue.copy(
                categoryName = newName,
                categoryId = newId
            )
        )
    }

    fun onAddExpense() {
        addExpenseJob()
    }

    private suspend fun getActiveAccountName(activeAccountId: Int): String = coroutineScope {
        val getAccountResult = accountsRepository.getAccountById(activeAccountId).first()

        when (getAccountResult) {
            is SafeResult.Failure -> ""
            is SafeResult.Success<Account> -> getAccountResult.data.name
        }
    }

    private suspend fun getRandomExpenseCategoryNameAndId(): Pair<Int, String> = coroutineScope {
        val getExpenseCategoriesResult = categoriesRepository.getAllExpenseCategories().first()
        when (getExpenseCategoriesResult) {
            is SafeResult.Failure -> 0 to ""
            is SafeResult.Success<List<Category>> -> {
                val randomCategory = getExpenseCategoriesResult.data.firstOrNull()
                randomCategory?.let {
                    randomCategory.id to randomCategory.name
                } ?: (0 to "")
            }
        }
    }

    private suspend fun getActiveAccountCurrencySymbol(): String = coroutineScope {
        val result = getActiveAccountCurrencyUseCase().first()
        when (result) {
            is SafeResult.Failure -> DEFAULT_CURRENCY_SYMBOL
            is SafeResult.Success<String> -> CurrencyMapper.getSymbol(result.data)
        }
    }

    private suspend fun getCurrentTime(): String = coroutineScope {
        val localDateTime = timeProvider.now().toLocalDateTime(timeZone)
        "${localDateTime.hour}:${localDateTime.minute}"
    }

    init {
        viewModelScope.launch {
            val activeAccountId = settingsRepository.getActiveAccountId().first()
            val activeAccountName = getActiveAccountName(activeAccountId)
            val randomExpenseCategoryNameAndId = getRandomExpenseCategoryNameAndId()
            val activeAccountCurrencySym = getActiveAccountCurrencySymbol()
            _screenState.value = EditExpenseState.Content(
                ExpenseScreenData(
                    expenseId = 0,
                    accountName = activeAccountName,
                    categoryId = randomExpenseCategoryNameAndId.first,
                    categoryName = randomExpenseCategoryNameAndId.second,
                    amount = "",
                    currencySymbol = activeAccountCurrencySym,
                    date = timeProvider.today().toString(),
                    dateMillis = timeProvider.now().toEpochMilliseconds(),
                    time = getCurrentTime(),
                    isDatePickerShown = false,
                    comment = "",
                    accountId = activeAccountId,
                )
            )
        }
    }

    private fun Long.asLocalDateAtTimeZone(timeZone: TimeZone): LocalDate {
        return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone).date
    }

    private fun LocalDate.asEpochMillisAtTimeZone(timeZone: TimeZone): Long =
        this.atStartOfDayIn(timeZone).toEpochMilliseconds()

    companion object {
        // FIXME: resolve fallback policy
        private const val DEFAULT_CURRENCY_SYMBOL = "₽"
    }
}