package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.ExpenseHistoryEntry

@Immutable
data class ExpensesHistoryScreenContent(
    val startDate: String,
    val endDate: String,
    val totalAmount: String,
    val expenses: List<ExpenseHistoryEntry>
)