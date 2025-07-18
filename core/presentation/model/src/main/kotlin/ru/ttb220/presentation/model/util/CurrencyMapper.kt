package ru.ttb220.presentation.model.util

import ru.ttb220.presentation.model.CurrencyData

/**
 * Responsible for mapping Currency code to it's presentation-model.
 * As for now there's no domain model representing all available currencies since there's no established
 * contract with backend for this case and currency is stored and passed between modules\components as string.
 */
object CurrencyMapper {

    private val currencyMap = mapOf(
        "RUB" to "₽",   // Российский рубль
        "USD" to "$",   // Доллар США
        "EUR" to "€",   // Евро
        "GBP" to "£",   // Британский фунт стерлингов
        "JPY" to "¥",   // Японская иена
        "CNY" to "¥",   // Китайский юань
        "KRW" to "₩",  // Южнокорейская вона
        "INR" to "₹",   // Индийская рупия
        "AUD" to "A$",  // Австралийский доллар
        "CAD" to "C$",  // Канадский доллар
        "CHF" to "CHF", // Швейцарский франк (нет уникального символа)
        "SEK" to "kr",  // Шведская крона
        "NOK" to "kr",  // Норвежская крона
        "DKK" to "kr",  // Датская крона
        "PLN" to "zł",  // Польский злотый
        "CZK" to "Kč",  // Чешская крона
        "TRY" to "₺",   // Турецкая лира
        "UAH" to "₴",   // Украинская гривна
        "BRL" to "R$",  // Бразильский реал
        "ZAR" to "R",   // Южноафриканский ранд
    )

    fun getSymbol(currencyCode: String): String =
        currencyMap[currencyCode.uppercase()] ?: currencyCode

    fun toCurrencyData(currencyCode: String): CurrencyData {
        return when (currencyCode) {
            "USD" -> CurrencyData.DOLLAR

            "EUR" -> CurrencyData.EURO

            "RUB" -> CurrencyData.RUSSIAN_RUBLE

            // TODO: refactor
            else -> throw RuntimeException("Not implemented $currencyCode")
        }
    }
}