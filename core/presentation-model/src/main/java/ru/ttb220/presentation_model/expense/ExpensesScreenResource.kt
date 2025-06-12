package ru.ttb220.presentation_model.expense

data class ExpensesScreenResource(
    val expenses: List<ExpenseResource>,
    val totalAmount: String,
)