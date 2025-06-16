package ru.ttb220.presentation.model.screen

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.util.Currency

@Immutable
data class AccountScreenState(
    @DrawableRes val leadingIconId: Int,
    val balance: String,
    val currency: Currency
)
