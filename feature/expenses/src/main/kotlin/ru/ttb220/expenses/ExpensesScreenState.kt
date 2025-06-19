package ru.ttb220.expenses

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.screen.ExpensesScreenData

@Immutable
sealed interface ExpensesScreenState {
    data object Loading : ExpensesScreenState

    data class Loaded(
        val data: ExpensesScreenData
    ) : ExpensesScreenState

    data class Error(
        val message: String
    ) : ExpensesScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : ExpensesScreenState
}