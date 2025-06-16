package ru.ttb220.presentation.model.screen

import ru.ttb220.presentation.model.ExpenseState

data class ExpensesScreenContent(
    val expenses: List<ExpenseState>,
    val totalAmount: String,
)