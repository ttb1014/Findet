package ru.ttb220.incomes.history

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.incomes.common.ACTIVE_ACCOUNT_ID
import ru.ttb220.incomes.common.TOP_LEVEL_INCOMES_ROUTE

const val HISTORY_SCREEN_ROUTE_BASE = "$TOP_LEVEL_INCOMES_ROUTE/history"
const val HISTORY_SCREEN_ROUTE = HISTORY_SCREEN_ROUTE_BASE + "?" +
            "$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToIncomesHistory(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = HISTORY_SCREEN_ROUTE_BASE + "?" +
            "$ACTIVE_ACCOUNT_ID=$accountId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.incomesHistoryScreen() {
    composable(
        route = HISTORY_SCREEN_ROUTE,
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