package ru.ttb220.income.presentation.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
sealed interface EditIncomeState {

    data object Loading : EditIncomeState

    data class Content(
        val data: IncomeScreenData
    ) : EditIncomeState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : EditIncomeState
}