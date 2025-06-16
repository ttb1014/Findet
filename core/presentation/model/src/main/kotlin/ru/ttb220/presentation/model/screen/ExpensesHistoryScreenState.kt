package ru.ttb220.presentation.model.screen

import ru.ttb220.presentation.model.ExpenseHistoryState

data class ExpensesHistoryScreenState(
    val startDate: String,
    val endDate: String,
    val totalAmount: String,
    val expenses: List<ExpenseHistoryState>
)