package ru.ttb220.category.presentation.model

import androidx.annotation.StringRes
import ru.ttb220.presentation.model.screen.CategoriesScreenData

sealed interface CategoriesScreenState {

    data object Loading : CategoriesScreenState

    data class Error(
        val message: String
    ) : CategoriesScreenState

    data class ErrorResource(
        @StringRes val messageId: Int
    ) : CategoriesScreenState

    data class Loaded(
        val data: CategoriesScreenData
    ) : CategoriesScreenState
}