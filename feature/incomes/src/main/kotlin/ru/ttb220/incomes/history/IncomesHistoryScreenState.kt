package ru.ttb220.incomes.history

import androidx.annotation.StringRes
import ru.ttb220.presentation.model.screen.HistoryScreenData

sealed interface IncomesHistoryScreenState {
    data object Loading : IncomesHistoryScreenState

    data class Error(val message: String) : IncomesHistoryScreenState

    data class ErrorResource(@StringRes val messageId: Int) : IncomesHistoryScreenState

    data class Loaded(
        val data: HistoryScreenData
    ) : IncomesHistoryScreenState
}