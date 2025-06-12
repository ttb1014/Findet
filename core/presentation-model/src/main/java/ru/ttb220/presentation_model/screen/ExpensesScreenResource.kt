package ru.ttb220.presentation_model.screen

import ru.ttb220.presentation_model.ExpenseResource

data class ExpensesScreenResource(
    val expenses: List<ExpenseResource>,
    val totalAmount: String,
)