package ru.ttb220.expenses.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.expenses.presentation.ui.ExpensesTodayScreen

const val EXPENSES_TODAY_SCREEN_ROUTE_BASE = "$TOP_LEVEL_EXPENSES_ROUTE/today"
const val EXPENSES_TODAY_SCREEN_ROUTE = EXPENSES_TODAY_SCREEN_ROUTE_BASE + "?" +
        "$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToExpensesToday(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = EXPENSES_TODAY_SCREEN_ROUTE_BASE + "?" +
            "$ACTIVE_ACCOUNT_ID=$accountId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.expensesTodayScreen() {
    composable(
        route = EXPENSES_TODAY_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(ACTIVE_ACCOUNT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        ),
    ) {
        ExpensesTodayScreen()
    }
}