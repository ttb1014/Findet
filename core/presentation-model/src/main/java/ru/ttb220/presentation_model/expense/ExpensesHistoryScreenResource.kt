package ru.ttb220.presentation_model.expense

data class ExpensesHistoryScreenResource(
    val startDate: String,
    val endDate: String,
    val totalAmount: String,
    val expenses:List<ExpenseHistoryResource>
)