package ru.ttb220.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.ttb220.account.presentation.navigation.TOP_LEVEL_ACCOUNT_ROUTE
import ru.ttb220.categories.presentation.navigation.TOP_LEVEL_CATEGORIES_ROUTE
import ru.ttb220.expenses.presentation.navigation.TOP_LEVEL_EXPENSES_ROUTE
import ru.ttb220.incomes.presentation.navigation.TOP_LEVEL_INCOMES_ROUTE
import ru.ttb220.presentation.model.R
import ru.ttb220.settings.presentation.navigation.TOP_LEVEL_SETTINGS_ROUTE

/**
 * Contains all possible top-level nav destinations available through bottom navigation.
 */
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
        route = TOP_LEVEL_CATEGORIES_ROUTE
    ),
    SETTINGS(
        iconId = R.drawable.settings,
        textId = R.string.settings,
        route = TOP_LEVEL_SETTINGS_ROUTE
    ),
}