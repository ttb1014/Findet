package ru.ttb220.bottomsheet.presentation.model

sealed interface CategoryBottomSheetState {
    data object Loading : CategoryBottomSheetState

    data class Error(val message: String? = null) : CategoryBottomSheetState

    data class Loaded(val categories: List<CategoryData>) : CategoryBottomSheetState
}