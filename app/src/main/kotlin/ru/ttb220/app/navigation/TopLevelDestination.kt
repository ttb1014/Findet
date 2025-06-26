package ru.ttb220.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ttb220.account.main.ACCOUNT_SCREEN_ROUTE
import ru.ttb220.expenses.common.TOP_LEVEL_EXPENSES_ROUTE
import ru.ttb220.incomes.today.INCOMES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.presentation.ui.R

enum class TopLevelDestination(
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int,
    @StringRes val topAppBarTextId: Int,
    val route: String,
    @DrawableRes val topAppBarLeadingIconInt: Int? = null,
    @DrawableRes val topAppBarTrailingIconInt: Int? = null,
) {
    EXPENSES(
        iconId = R.drawable.downtrend,
        textId = ru.ttb220.app.R.string.expenses,
        topAppBarTextId = ru.ttb220.app.R.string.expenses_today,
        route = TOP_LEVEL_EXPENSES_ROUTE,
        topAppBarTrailingIconInt = R.drawable.history,
    ),
    INCOMES(
        R.drawable.uptrend,
        ru.ttb220.app.R.string.incomes,
        ru.ttb220.app.R.string.incomes_today,
        route = INCOMES_TODAY_SCREEN_ROUTE_BASE,
        topAppBarTrailingIconInt = R.drawable.history,
    ),
    ACCOUNT(
        R.drawable.calculator,
        ru.ttb220.app.R.string.account,
        ru.ttb220.app.R.string.my_account,
        route = ACCOUNT_SCREEN_ROUTE,
        topAppBarTrailingIconInt = R.drawable.edit,
    ),
    CATEGORIES(
        R.drawable.barchartside,
        ru.ttb220.app.R.string.categories,
        ru.ttb220.app.R.string.my_categories,
        route = "articles"
    ),
    SETTINGS(
        R.drawable.settings,
        ru.ttb220.app.R.string.settings,
        ru.ttb220.app.R.string.settings,
        route = "settings"
    ),
}