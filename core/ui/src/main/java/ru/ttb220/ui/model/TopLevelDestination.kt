package ru.ttb220.ui.model

import androidx.annotation.DrawableRes
import ru.ttb220.ui.R

enum class TopLevelDestination(
    @DrawableRes val iconId: Int,
    val destinationName: String,
) {
    EXPENSES(
        R.drawable.downtrend,
        "Расходы"
    ),
    INCOMES(
        R.drawable.uptrend,
        "Доходы",
    ),
    ACCOUNT(
        R.drawable.calculator,
        "Счет"
    ),
    ARTICLES(
        R.drawable.barchartside,
        "Статьи"
    ),
    SETTINGS(
        R.drawable.settings,
        "Настройки"
    )
}