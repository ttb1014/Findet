package ru.ttb220.expense.presentation.model

import androidx.annotation.StringRes
import ru.ttb220.presentation.model.screen.HistoryScreenData

sealed interface ExpensesHistoryScreenState {
    data object Loading : ExpensesHistoryScreenState

    data class Error(val message: String) : ExpensesHistoryScreenState

    data class ErrorResource(@StringRes val messageId: Int) : ExpensesHistoryScreenState

    data class Loaded(
        val data: HistoryScreenData
    ) : ExpensesHistoryScreenState
}