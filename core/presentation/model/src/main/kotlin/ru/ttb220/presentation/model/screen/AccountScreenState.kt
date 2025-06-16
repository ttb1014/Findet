package ru.ttb220.presentation.model.screen

import androidx.annotation.DrawableRes
import ru.ttb220.presentation.model.util.Currency

data class AccountScreenState(
    @DrawableRes val leadingIconId: Int,
    val balance: String,
    val currency: Currency
)
