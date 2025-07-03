package ru.ttb220.incomes.presentation.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.screen.IncomesScreenData

@Immutable
sealed interface IncomesTodayScreenState {
    data object Loading : IncomesTodayScreenState

    data class Loaded(
        val data: IncomesScreenData
    ) : IncomesTodayScreenState

    data class Error(
        val message: String
    ) : IncomesTodayScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : IncomesTodayScreenState
}