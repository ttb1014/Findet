package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.ExpenseHistoryData

@Immutable
data class ExpensesHistoryScreenData(
    val startDate: String,
    val endDate: String,
    val totalAmount: String,
    val expenses: List<ExpenseHistoryData>
)