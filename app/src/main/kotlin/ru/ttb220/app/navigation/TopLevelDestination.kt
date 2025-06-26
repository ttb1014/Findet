package ru.ttb220.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ttb220.account.common.TOP_LEVEL_ACCOUNT_ROUTE
import ru.ttb220.account.main.ACCOUNT_SCREEN_ROUTE
import ru.ttb220.expenses.common.TOP_LEVEL_EXPENSES_ROUTE
import ru.ttb220.incomes.common.TOP_LEVEL_INCOMES_ROUTE
import ru.ttb220.incomes.today.INCOMES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.presentation.model.R

enum class TopLevelDestination(
    @DrawableRes val iconId: Int,
    @StringRes val textId: Int,
    val route: String,
) {
    EXPENSES(
        iconId = R.drawable.downtrend,
        textId = R.string.expenses,
        route = TOP_LEVEL_EXPENSES_ROUTE,
    ),
    INCOMES(
        iconId = R.drawable.uptrend,
        textId = R.string.incomes,
        route = TOP_LEVEL_INCOMES_ROUTE,
    ),
    ACCOUNT(
        iconId = R.drawable.calculator,
        textId = R.string.account,
        route = TOP_LEVEL_ACCOUNT_ROUTE,
    ),
    CATEGORIES(
        iconId = R.drawable.barchartside,
        textId = R.string.categories,
        route = "categories"
    ),
    SETTINGS(
        iconId = R.drawable.settings,
        textId = R.string.settings,
        route = "settings"
    ),
}