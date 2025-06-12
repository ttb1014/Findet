package ru.ttb220.presentation_model.screen

import androidx.annotation.DrawableRes
import ru.ttb220.presentation_model.util.Currency

data class AccountScreenResource(
    @DrawableRes val leadingIconId: Int,
    val balance: String,
    val currency: Currency
)
