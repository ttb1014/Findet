package ru.ttb220.incomes.presentation.viewmodel

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
import ru.ttb220.data.api.legacy.TransactionsRepository
import ru.ttb220.incomes.presentation.model.EditIncomeIntent
import ru.ttb220.incomes.presentation.model.EditIncomeState
import ru.ttb220.incomes.presentation.model.toIncomeScreenData
import ru.ttb220.incomes.presentation.model.toTransactionBrief
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.presentation.model.R
import javax.inject.Singleton

class EditIncomeViewModel @AssistedInject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val timeZone: TimeZone,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val incomeId: Int? = savedStateHandle.get<Int>("incomeId")

    private val _screenState = MutableStateFlow<EditIncomeState>(EditIncomeState.Loading)
    val screenState = _screenState.asStateFlow()

    private fun editIncomeJob() = viewModelScope.launch {
        val transaction =
            (_screenState.value as EditIncomeState.Content).data.toTransactionBrief()

        incomeId?.let {
            transactionsRepository.updateTransactionById(
                incomeId,
                transaction
            ).collect {}
        } ?: transactionsRepository.createNewTransaction(transaction).collect {}
    }

    private fun deleteIncomeJob() = viewModelScope.launch {
        incomeId?.let {
            transactionsRepository.deleteTransactionById(it).collect {}
        }
    }

    fun showDatePicker() {
        val oldValue = (_screenState.value as EditIncomeState.Content).data
        _screenState.value = EditIncomeState.Content(
            oldValue.copy(isDatePickerShown = true)
        )
    }

    fun hideDatePicker() {
        val oldValue = (_screenState.value as EditIncomeState.Content).data
        _screenState.value = EditIncomeState.Content(
            oldValue.copy(isDatePickerShown = false)
        )
    }

    fun onAmountChange(newAmount: String) {
        val oldValue = (_screenState.value as EditIncomeState.Content).data

        _screenState.value = EditIncomeState.Content(
            oldValue.copy(amount = newAmount)
        )
    }

    fun onTimeChange(newTime: String) {
        val oldValue = (_screenState.value as EditIncomeState.Content).data
        _screenState.value = EditIncomeState.Content(
            oldValue.copy(time = newTime)
        )
    }

    fun onCommentChange(newComment: String) {
        val oldValue = (_screenState.value as EditIncomeState.Content).data
        _screenState.value = EditIncomeState.Content(
            oldValue.copy(comment = newComment)
        )
    }

    fun onDateChange(newDateMillis: Long?) {
        if (newDateMillis == null) return

        val newDate = newDateMillis.asLocalDateAtTimeZone(timeZone)

        val oldValue = (_screenState.value as EditIncomeState.Content).data
        _screenState.value = EditIncomeState.Content(
            oldValue.copy(
                date = newDate.toString(),
                dateMillis = newDateMillis
            )
        )
    }

    fun onIntent(intent: EditIncomeIntent) {
        when (intent) {
            is EditIncomeIntent.ChangeAmount -> onAmountChange(intent.amount)
            is EditIncomeIntent.ChangeComment -> onCommentChange(intent.comment)
            is EditIncomeIntent.ChangeTime -> onTimeChange(intent.time)
            EditIncomeIntent.ShowDatePicker -> showDatePicker()
            is EditIncomeIntent.ChangeDate -> onDateChange(intent.date)
            EditIncomeIntent.HideDatePicker -> hideDatePicker()
        }
    }

    fun onAccountChange(
        newName: String,
        newId: Int
    ) {
        val oldValue = (_screenState.value as EditIncomeState.Content).data
        _screenState.value = EditIncomeState.Content(
            oldValue.copy(
                accountName = newName,
                accountId = newId
            )
        )
    }

    fun onCategoryChange(newName: String, newId: Int) {
        val oldValue = (_screenState.value as EditIncomeState.Content).data
        _screenState.value = EditIncomeState.Content(
            oldValue.copy(
                categoryName = newName,
                categoryId = newId
            )
        )
    }

    fun onEditIncome() {
        editIncomeJob()
    }

    fun onDeleteIncomeClick() {
        deleteIncomeJob()
    }

    init {
        viewModelScope.launch {
            if (incomeId == null) {
                _screenState.value =
                    EditIncomeState.ErrorResource(R.string.error_income_not_found)
                return@launch
            }

            val transaction = transactionsRepository.getTransactionById(incomeId).first()

            when (transaction) {
                is SafeResult.Failure -> {
                    _screenState.value =
                        EditIncomeState.ErrorResource(R.string.error_income_not_found)
                }

                is SafeResult.Success<TransactionDetailed> -> {
                    _screenState.value =
                        EditIncomeState.Content(transaction.data.toIncomeScreenData(timeZone))
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
        fun create(savedStateHandle: SavedStateHandle): EditIncomeViewModel
    }
}