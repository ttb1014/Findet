package ru.ttb220.demo.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ttb220.ui.R

enum class Destination(
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int,
) {
    EXPENSES(
        R.drawable.downtrend,
        ru.ttb220.demo.R.string.expenses,
    ),
    INCOMES(
        R.drawable.uptrend,
        ru.ttb220.demo.R.string.incomes,
    ),
    ACCOUNT(
        R.drawable.calculator,
        ru.ttb220.demo.R.string.account,
    ),
    ARTICLES(
        R.drawable.barchartside,
        ru.ttb220.demo.R.string.articles,
    ),
    SETTINGS(
        R.drawable.settings,
        ru.ttb220.demo.R.string.settings,
    )
}