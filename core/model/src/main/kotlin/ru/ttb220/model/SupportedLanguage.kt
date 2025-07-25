package ru.ttb220.model

import java.util.Locale

enum class SupportedLanguage(
    val locale: Locale,
) {
    RUSSIAN(
        locale = Locale("ru")
    ),
    ENGLISH(
        locale = Locale("en")
    ),
}