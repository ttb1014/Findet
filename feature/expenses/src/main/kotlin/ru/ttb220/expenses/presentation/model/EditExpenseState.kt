package ru.ttb220.expenses.presentation.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
sealed interface EditExpenseState {

    data object Loading : EditExpenseState

    data class Content(
        val data: ExpenseScreenData
    ) : EditExpenseState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : EditExpenseState
}