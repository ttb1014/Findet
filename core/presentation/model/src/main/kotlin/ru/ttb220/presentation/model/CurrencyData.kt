package ru.ttb220.presentation.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable

@Immutable
enum class CurrencyData(
    @DrawableRes val iconId: Int,
    @StringRes val fullNameId: Int,
    val symbol: Char? = null,
) {
    RUSSIAN_RUBLE(
        iconId = R.drawable.ruble,
        fullNameId = R.string.ruble,
        symbol = '₽',
    ) {
        override fun toString(): String = "RUB"
    },
    DOLLAR(
        iconId = R.drawable.dollar,
        fullNameId = R.string.american_dollar,
        symbol = '$',
    ) {
        override fun toString(): String = "USD"
    },
    EURO(
        iconId = R.drawable.euro,
        fullNameId = R.string.euro,
        symbol = '€',
    ) {
        override fun toString(): String = "EUR"
    }
}