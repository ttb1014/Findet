package ru.ttb220.model.expense

data class ExpensesScreenResource(
    val expenses: List<ExpenseResource>,
    val totalAmount: String,
)