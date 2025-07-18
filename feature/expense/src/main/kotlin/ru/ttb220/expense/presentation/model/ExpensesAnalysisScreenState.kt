package ru.ttb220.expense.presentation.model

import androidx.annotation.StringRes

sealed interface ExpensesAnalysisScreenState {
    data object Loading : ExpensesAnalysisScreenState

    data class Content(
        val beginMonthWithYear: String,
        val endMonthWithYear: String,
        val totalAmount: String,
        val items: List<ExpenseAnalysisItemData>,
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
