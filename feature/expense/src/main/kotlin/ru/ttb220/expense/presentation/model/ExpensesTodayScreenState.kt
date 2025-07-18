package ru.ttb220.expense.presentation.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.screen.ExpensesScreenData

@Immutable
sealed interface ExpensesTodayScreenState {
    data object Loading : ExpensesTodayScreenState

    data class Loaded(
        val data: ExpensesScreenData
    ) : ExpensesTodayScreenState

    data class Error(
        val message: String
    ) : ExpensesTodayScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : ExpensesTodayScreenState
}