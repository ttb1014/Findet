package ru.ttb220.expenses_history

import ru.ttb220.presentation.model.screen.HistoryScreenData

sealed interface ExpensesHistoryScreenState {
    data object Loading : ExpensesHistoryScreenState

    data class Error(val message: String) : ExpensesHistoryScreenState

    data class Loaded(
        val data: HistoryScreenData
    ) : ExpensesHistoryScreenState
}