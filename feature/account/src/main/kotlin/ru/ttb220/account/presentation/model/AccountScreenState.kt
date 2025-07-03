package ru.ttb220.account.presentation.model

import androidx.annotation.StringRes
import ru.ttb220.presentation.model.screen.AccountScreenData

sealed interface AccountScreenState {

    data object Loading : AccountScreenState

    data class Error(
        val message: String
    ) : AccountScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : AccountScreenState

    data class Loaded(
        val data: AccountScreenData
    ) : AccountScreenState
}