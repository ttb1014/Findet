package ru.ttb220.incomes_history

import ru.ttb220.presentation.model.screen.HistoryScreenData

sealed interface IncomesHistoryScreenState {
    data object Loading : IncomesHistoryScreenState

    data class Error(val message: String) : IncomesHistoryScreenState

    data class Loaded(
        val data: HistoryScreenData
    ) : IncomesHistoryScreenState
}