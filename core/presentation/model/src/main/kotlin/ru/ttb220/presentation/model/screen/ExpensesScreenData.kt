package ru.ttb220.presentation.model.screen

import ru.ttb220.presentation.model.ExpenseData

data class ExpensesScreenData(
    val expenses: List<ExpenseData>,
    val totalAmount: String,
)