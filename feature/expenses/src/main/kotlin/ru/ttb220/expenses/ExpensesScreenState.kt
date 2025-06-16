package ru.ttb220.expenses

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.screen.ExpensesScreenContent

@Immutable
sealed interface ExpensesScreenState {
    data object Loading : ExpensesScreenState

    data class Loaded(
        val data: ExpensesScreenContent
    ) : ExpensesScreenState

    data class Error(
        val message: String
    ) : ExpensesScreenState
}