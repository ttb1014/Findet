package ru.ttb220.income.presentation.model

sealed interface EditIncomeIntent {
    data class ChangeAmount(val amount: String) : EditIncomeIntent
    object ShowDatePicker : EditIncomeIntent

    object HideDatePicker : EditIncomeIntent

    data class ChangeDate(val date: Long?) : EditIncomeIntent

    data class ChangeTime(val time: String) : EditIncomeIntent
    data class ChangeComment(val comment: String) : EditIncomeIntent
}
