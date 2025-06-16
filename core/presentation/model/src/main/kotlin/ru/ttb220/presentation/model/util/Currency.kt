package ru.ttb220.presentation.model.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ttb220.presentation.model.R

enum class Currency(
    @DrawableRes val iconId: Int,
    @StringRes val fullNameId: Int,
    val symbol: Char? = null,
) {
    RUSSIAN_RUBLE(
        iconId = R.drawable.ruble,
        fullNameId = R.string.ruble,
        symbol = '₽',
    ),
    DOLLAR(
        iconId = R.drawable.dollar,
        fullNameId = R.string.american_dollar,
        symbol = '$',
    ),
    EURO(
        iconId = R.drawable.euro,
        fullNameId = R.string.euro,
    )
}