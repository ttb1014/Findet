package ru.ttb220.expenses.history

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ACTIVE_ACCOUNT_ID = "accountId"

const val EXPENSES_HISTORY_SCREEN_ROUTE_BASE = "expenses_history"
const val EXPENSES_HISTORY_SCREEN_ROUTE =
    "$EXPENSES_HISTORY_SCREEN_ROUTE_BASE?$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToExpensesHistory(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = EXPENSES_HISTORY_SCREEN_ROUTE_BASE + "?" +
            "$ACTIVE_ACCOUNT_ID=$accountId"

    navigate(route, navOptions)
}

fun NavGraphBuilder.expensesHistoryScreen() {
    composable(
        route = EXPENSES_HISTORY_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(ACTIVE_ACCOUNT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        ),
    ) {
        ExpensesHistoryScreen()
    }
}