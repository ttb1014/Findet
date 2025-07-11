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
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.incomes.presentation.model.AddIncomeIntent
import ru.ttb220.incomes.presentation.model.AddIncomeScreenData
import ru.ttb220.incomes.presentation.model.AddIncomeScreenState
import ru.ttb220.incomes.presentation.model.toTransactionBrief
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddIncomeViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val timeZone: TimeZone,
) : ViewModel() {

    private val _screenState = MutableStateFlow<AddIncomeScreenState>(AddIncomeScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    private fun addIncomeJob() = viewModelScope.launch {
        val transaction =
            (_screenState.value as AddIncomeScreenState.Content).data.toTransactionBrief()

        transactionsRepository.createNewTransaction(transaction).collect { }
    }

    fun showDatePicker() {
        val oldValue = (_screenState.value as AddIncomeScreenState.Content).data
        _screenState.value = AddIncomeScreenState.Content(
            oldValue.copy(isDatePickerShown = true)
        )
    }

    fun hideDatePicker() {
        val oldValue = (_screenState.value as AddIncomeScreenState.Content).data
        _screenState.value = AddIncomeScreenState.Content(
            oldValue.copy(isDatePickerShown = false)
        )
    }

    fun onAmountChange(newAmount: String) {
        val oldValue = (_screenState.value as AddIncomeScreenState.Content).data

        _screenState.value = AddIncomeScreenState.Content(
            oldValue.copy(amount = newAmount)
        )
    }

    fun onTimeChange(newTime: String) {
        val oldValue = (_screenState.value as AddIncomeScreenState.Content).data
        _screenState.value = AddIncomeScreenState.Content(
            oldValue.copy(time = newTime)
        )
    }

    fun onCommentChange(newComment: String) {
        val oldValue = (_screenState.value as AddIncomeScreenState.Content).data
        _screenState.value = AddIncomeScreenState.Content(
            oldValue.copy(comment = newComment)
        )
    }

    fun onDateChange(newDateMillis: Long?) {
        if (newDateMillis == null) return

        val newDate = newDateMillis.asLocalDateAtTimeZone(timeZone)

        val oldValue = (_screenState.value as AddIncomeScreenState.Content).data
        _screenState.value = AddIncomeScreenState.Content(
            oldValue.copy(
                date = newDate.toString(),
                dateMillis = newDateMillis
            )
        )
    }

    fun onIntent(intent: AddIncomeIntent) {
        when (intent) {
            is AddIncomeIntent.ChangeAmount -> onAmountChange(intent.amount)
            is AddIncomeIntent.ChangeComment -> onCommentChange(intent.comment)
            is AddIncomeIntent.ChangeTime -> onTimeChange(intent.time)
            AddIncomeIntent.ShowDatePicker -> showDatePicker()
            is AddIncomeIntent.ChangeDate -> onDateChange(intent.date)
            AddIncomeIntent.HideDatePicker -> hideDatePicker()
        }
    }

    fun onAccountChange(
        newName: String,
        newId: Int
    ) {
        val oldValue = (_screenState.value as AddIncomeScreenState.Content).data
        _screenState.value = AddIncomeScreenState.Content(
            oldValue.copy(
                accountName = newName,
                accountId = newId
            )
        )
    }

    fun onCategoryChange(newName: String, newId: Int) {
        val oldValue = (_screenState.value as AddIncomeScreenState.Content).data
        _screenState.value = AddIncomeScreenState.Content(
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
            _screenState.value = AddIncomeScreenState.Content(DEFAULT_CONTENT)
        }
    }

    private fun Long.asLocalDateAtTimeZone(timeZone: TimeZone): LocalDate {
        return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone).date
    }

    private fun LocalDate.asEpochMillisAtTimeZone(timeZone: TimeZone): Long =
        this.atStartOfDayIn(timeZone).toEpochMilliseconds()

    companion object {
        // можно организовать восстановления предыдущего состояния, пока - мок
        val DEFAULT_CONTENT = AddIncomeScreenData(
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