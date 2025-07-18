package ru.ttb220.presentation.util

import java.text.DecimalFormat

object NumberToStringMapper {

    private val DEFAULT_DECIMAL_FORMAT = DecimalFormat("0.00")

    fun map(number: Number, currencySymbol: String? = null): String {
        val formattedNumber = DEFAULT_DECIMAL_FORMAT.format(number.toDouble())

        if (!currencySymbol.isNullOrBlank()) {
            return "$formattedNumber $currencySymbol"
        }

        return formattedNumber
    }
}