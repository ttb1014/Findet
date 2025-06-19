package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.ExpenseAnalysisData

@Immutable
class ExpensesAnalysisScreenData(
    val startDate: String,
    val endDate: String,
    val amount: String,
    val expenses: List<ExpenseAnalysisData>
)