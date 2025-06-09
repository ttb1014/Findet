package ru.ttb220.model.expense

data class ExpensesHistoryScreenResource(
    val startDate: String,
    val endDate: String,
    val totalAmount: String,
    val expenses:List<ExpenseHistoryResource>
)