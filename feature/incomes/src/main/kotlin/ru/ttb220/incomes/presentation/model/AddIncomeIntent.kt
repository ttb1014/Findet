package ru.ttb220.incomes.presentation.model

sealed interface AddIncomeIntent {
    data class ChangeAmount(val amount: String) : AddIncomeIntent
    object ShowDatePicker : AddIncomeIntent

    object HideDatePicker : AddIncomeIntent

    data class ChangeDate(val date: Long?) : AddIncomeIntent

    data class ChangeTime(val time: String) : AddIncomeIntent
    data class ChangeComment(val comment: String) : AddIncomeIntent
}
