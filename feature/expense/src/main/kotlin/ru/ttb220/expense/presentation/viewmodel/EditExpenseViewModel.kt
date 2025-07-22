package ru.ttb220.expense.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
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
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.expense.presentation.model.EditExpenseIntent
import ru.ttb220.expense.presentation.model.EditExpenseState
import ru.ttb220.expense.presentation.model.toExpenseScreenData
import ru.ttb220.expense.presentation.model.toTransactionBrief
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.mapper.toTransactionDetailed
import ru.ttb220.presentation.model.R
import javax.inject.Singleton

class EditExpenseViewModel @AssistedInject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val timeZone: TimeZone,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val expenseId: Int? = savedStateHandle.get<Int>("expenseId")

    private val _screenState = MutableStateFlow<EditExpenseState>(EditExpenseState.Loading)
    val screenState = _screenState.asStateFlow()

    private fun editExpenseJob() = viewModelScope.launch {
        val transaction =
            (_screenState.value as EditExpenseState.Content).data.toTransactionBrief()

        expenseId?.let {
            transactionsRepository.updateTransactionById(
                expenseId,
                transaction
            ).collect {}
        } ?: transactionsRepository.createNewTransaction(transaction).collect {}
    }

    private fun deleteExpenseJob() = viewModelScope.launch {
        expenseId?.let {
            transactionsRepository.deleteTransactionById(it).collect {}
        }
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

    fun onEditExpense() {
        editExpenseJob()
    }

    fun onDeleteExpenseClick() {
        deleteExpenseJob()
    }

    init {
        viewModelScope.launch {
            if (expenseId == null) {
                _screenState.value =
                    EditExpenseState.ErrorResource(R.string.error_expense_not_found)
                return@launch
            }

            val account =
                (accountsRepository.getAccountById(settingsRepository.getActiveAccountId().first())
                    .first() as SafeResult.Success).data
            val categories =
                (categoriesRepository.getAllCategories().first() as SafeResult.Success).data
            val transaction = transactionsRepository.getTransactionById(expenseId).first()

            when (transaction) {
                is SafeResult.Failure -> {
                    _screenState.value =
                        EditExpenseState.ErrorResource(R.string.error_expense_not_found)
                }

                is SafeResult.Success<Transaction> -> {
                    _screenState.value =
                        EditExpenseState.Content(
                            transaction.data.toTransactionDetailed(
                                account,
                                categories
                            ).toExpenseScreenData(timeZone)
                        )
                }
            }
        }
    }

    private fun Long.asLocalDateAtTimeZone(timeZone: TimeZone): LocalDate {
        return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone).date
    }

    private fun LocalDate.asEpochMillisAtTimeZone(timeZone: TimeZone): Long =
        this.atStartOfDayIn(timeZone).toEpochMilliseconds()

    @Singleton
    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): EditExpenseViewModel
    }
}