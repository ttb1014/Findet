package ru.ttb220.incomes.presentation.model

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
sealed interface AddIncomeScreenState {

    data object Loading : AddIncomeScreenState

    data class Content(
        val data: AddIncomeScreenData
    ) : AddIncomeScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : AddIncomeScreenState
}