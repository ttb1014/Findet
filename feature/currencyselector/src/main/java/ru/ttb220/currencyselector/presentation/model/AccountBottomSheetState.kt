package ru.ttb220.currencyselector.presentation.model

sealed interface AccountBottomSheetState {
    data object Loading : AccountBottomSheetState

    data class Loaded(val accounts: List<AccountData>): AccountBottomSheetState

    data class Error(val message: String? = null): AccountBottomSheetState
}