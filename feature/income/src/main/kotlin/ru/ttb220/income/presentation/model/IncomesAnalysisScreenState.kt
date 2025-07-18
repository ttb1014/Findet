package ru.ttb220.income.presentation.model

import androidx.annotation.StringRes

sealed interface IncomesAnalysisScreenState {
    data object Loading : IncomesAnalysisScreenState

    data class Content(
        val beginMonthWithYear: String,
        val endMonthWithYear: String,
        val totalAmount: String,
        val items: List<IncomeAnalysisItemData>,
    ) : IncomesAnalysisScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : IncomesAnalysisScreenState

    data class Empty(
        val beginMonthWithYear: String,
        val endMonthWithYear: String,
        val totalAmount: String,
    ) : IncomesAnalysisScreenState
}
