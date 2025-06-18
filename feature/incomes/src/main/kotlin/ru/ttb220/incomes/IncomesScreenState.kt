package ru.ttb220.incomes

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.screen.ExpensesScreenContent
import ru.ttb220.presentation.model.screen.IncomesScreenContent

@Immutable
sealed interface IncomesScreenState {
    data object Loading : IncomesScreenState

    data class Loaded(
        val data: IncomesScreenContent
    ) : IncomesScreenState

    data class Error(
        val message: String
    ) : IncomesScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : IncomesScreenState
}