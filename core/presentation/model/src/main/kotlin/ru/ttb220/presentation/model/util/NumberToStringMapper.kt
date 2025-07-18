package ru.ttb220.presentation.model.util

import java.text.DecimalFormat

object NumberToStringMapper {

    private val DEFAULT_DECIMAL_FORMAT = DecimalFormat("0.00")
    private val DEFAULT_PERCENTAGE_FORMAT = DecimalFormat("0")

    fun map(number: Number, currencySymbol: String? = null): String {
        val formattedNumber = DEFAULT_DECIMAL_FORMAT.format(number.toDouble())

        if (!currencySymbol.isNullOrBlank()) {
            return "$formattedNumber $currencySymbol"
        }

        return formattedNumber
    }

    fun mapPercentage(percent: Double): String {
        val formattedNumber = DEFAULT_PERCENTAGE_FORMAT.format(percent * 100)

        return "$formattedNumber %"
    }
}