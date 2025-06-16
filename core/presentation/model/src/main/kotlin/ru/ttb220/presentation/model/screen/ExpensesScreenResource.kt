package ru.ttb220.presentation.model.screen

import ru.ttb220.presentation.model.ExpenseResource

data class ExpensesScreenResource(
    val expenses: List<ExpenseResource>,
    val totalAmount: String,
)