package ru.ttb220.expense.presentation.model

sealed interface EditExpenseIntent {
    data class ChangeAmount(val amount: String) : EditExpenseIntent
    object ShowDatePicker : EditExpenseIntent

    object HideDatePicker : EditExpenseIntent

    data class ChangeDate(val date: Long?) : EditExpenseIntent

    data class ChangeTime(val time: String) : EditExpenseIntent
    data class ChangeComment(val comment: String) : EditExpenseIntent
}
