package ru.ttb220.demo.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ttb220.ui.R

enum class Destination(
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int,
    @StringRes val topAppBarTextId: Int,
    @DrawableRes val topAppBarLeadingIconInt: Int? = null,
    @DrawableRes val topAppBarTrailingIconInt: Int? = null,
) {
    EXPENSES(
        iconId = R.drawable.downtrend,
        textId = ru.ttb220.demo.R.string.expenses,
        topAppBarTextId = ru.ttb220.demo.R.string.top_expenses,
        topAppBarTrailingIconInt = R.drawable.history,
    ),
    INCOMES(
        R.drawable.uptrend,
        ru.ttb220.demo.R.string.incomes,
        ru.ttb220.demo.R.string.top_incomes,
        topAppBarTrailingIconInt = R.drawable.history,
    ),
    ACCOUNT(
        R.drawable.calculator,
        ru.ttb220.demo.R.string.account,
        ru.ttb220.demo.R.string.top_account,
        topAppBarTrailingIconInt = R.drawable.edit,
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