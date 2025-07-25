package ru.ttb220.expense.presentation.model

import androidx.annotation.StringRes
import ru.ttb220.chart.api.model.CircleDiagramData

sealed interface ExpensesAnalysisScreenState {
    data object Loading : ExpensesAnalysisScreenState

    data class Content(
        val beginMonthWithYear: String,
        val endMonthWithYear: String,
        val totalAmount: String,
        val items: List<ExpenseAnalysisItemData>,
        val circleDiagramData: CircleDiagramData,
    ) : ExpensesAnalysisScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : ExpensesAnalysisScreenState

    data class Empty(
        val beginMonthWithYear: String,
        val endMonthWithYear: String,
        val totalAmount: String,
    ) : ExpensesAnalysisScreenState
}
