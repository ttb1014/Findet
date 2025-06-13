package ru.ttb220.presentation_model.screen

import ru.ttb220.presentation_model.ExpenseHistoryResource

data class ExpensesHistoryScreenResource(
    val startDate: String,
    val endDate: String,
    val totalAmount: String,
    val expenses: List<ExpenseHistoryResource>
)