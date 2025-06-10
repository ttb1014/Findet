package ru.ttb220.demo.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ttb220.ui.R

enum class Destination(
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int,
    @StringRes val topAppBarTextId: Int,
) {
    EXPENSES(
        R.drawable.downtrend,
        ru.ttb220.demo.R.string.expenses,
        ru.ttb220.demo.R.string.top_expenses
    ),
    INCOMES(
        R.drawable.uptrend,
        ru.ttb220.demo.R.string.incomes,
        ru.ttb220.demo.R.string.top_incomes
    ),
    ACCOUNT(
        R.drawable.calculator,
        ru.ttb220.demo.R.string.account,
        ru.ttb220.demo.R.string.top_account
    ),
    ARTICLES(
        R.drawable.barchartside,
        ru.ttb220.demo.R.string.articles,
        ru.ttb220.demo.R.string.top_articles
    ),
    SETTINGS(
        R.drawable.settings,
        ru.ttb220.demo.R.string.settings,
        ru.ttb220.demo.R.string.top_settings
    )
}