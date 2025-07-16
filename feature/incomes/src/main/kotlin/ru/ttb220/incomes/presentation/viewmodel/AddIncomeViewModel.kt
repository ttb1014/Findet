package ru.ttb220.incomes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.data.api.legacy.TransactionsRepository
import ru.ttb220.incomes.presentation.model.EditIncomeIntent
import ru.ttb220.incomes.presentation.model.IncomeScreenData
import ru.ttb220.incomes.presentation.model.EditIncomeState
import ru.ttb220.incomes.presentation.model.toTransactionBrief
import javax.inject.Inject
import javax.inject.Singleton

class AddIncomeViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val timeZone: TimeZone,
) : ViewModel() {

    private val _screenState = MutableStateFlow<EditIncomeState>(EditIncomeState.Loading)
    val screenState = _screenState.asStateFlow()

    private fun addIncomeJob() = viewModelScope.launch {
        val transaction =
            (_screenState.value as EditIncomeState.Content).data.toTransactionBrief()

        transactionsRepository.createNewTransaction(transaction).collect { }
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

    fun onAddIncome() {
        addIncomeJob()
    }

    init {
        viewModelScope.launch {
            delay(1000L)
            _screenState.value = EditIncomeState.Content(DEFAULT_CONTENT)
        }
    }

    private fun Long.asLocalDateAtTimeZone(timeZone: TimeZone): LocalDate {
        return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone).date
    }

    private fun LocalDate.asEpochMillisAtTimeZone(timeZone: TimeZone): Long =
        this.atStartOfDayIn(timeZone).toEpochMilliseconds()

    companion object {
        // можно организовать восстановления предыдущего состояния, пока - мок
        val DEFAULT_CONTENT = IncomeScreenData(
            incomeId = 123,
            accountName = "Сбербанк",
            categoryName = "Ремонт",
            amount = "25 270",
            date = "25.02.2025",
            dateMillis = 0L,
            time = "23:41",
            comment = "Ремонт - фурнитура для дверей",
            currencySymbol = "₽",
            accountId = 54,
            categoryId = 10,
            isDatePickerShown = false
        )
    }
}