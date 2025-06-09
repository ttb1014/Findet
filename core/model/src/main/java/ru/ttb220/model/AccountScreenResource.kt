package ru.ttb220.model

import androidx.annotation.DrawableRes
import ru.ttb220.model.util.Currency

data class AccountScreenResource(
    @DrawableRes val leadingIconId: Int,
    val balance: String,
    val currency: Currency
)
