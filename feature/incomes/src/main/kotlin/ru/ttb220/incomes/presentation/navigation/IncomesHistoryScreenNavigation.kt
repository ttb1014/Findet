package ru.ttb220.incomes.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.incomes.presentation.ui.IncomesHistoryScreen

const val INCOMES_HISTORY_SCREEN_ROUTE_BASE = "$TOP_LEVEL_INCOMES_ROUTE/history"
const val INCOMES_HISTORY_SCREEN_ROUTE = INCOMES_HISTORY_SCREEN_ROUTE_BASE + "?" +
            "$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToIncomesHistory(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = INCOMES_HISTORY_SCREEN_ROUTE_BASE + "?" +
            "$ACTIVE_ACCOUNT_ID=$accountId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.incomesHistoryScreen() {
    composable(
        route = INCOMES_HISTORY_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(ACTIVE_ACCOUNT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        ),
    ) {
        IncomesHistoryScreen()
    }
}