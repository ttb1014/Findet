package ru.ttb220.incomes.today

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.incomes.common.ACTIVE_ACCOUNT_ID
import ru.ttb220.incomes.common.TOP_LEVEL_INCOMES_ROUTE

const val INCOMES_TODAY_SCREEN_ROUTE_BASE = "$TOP_LEVEL_INCOMES_ROUTE/today"
const val INCOMES_TODAY_SCREEN_ROUTE = INCOMES_TODAY_SCREEN_ROUTE_BASE + "?" +
        "$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToIncomesToday(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = "${INCOMES_TODAY_SCREEN_ROUTE_BASE}?$ACTIVE_ACCOUNT_ID=$accountId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.incomesTodayScreen() {
    composable(
        route = INCOMES_TODAY_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(ACTIVE_ACCOUNT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        ),
    ) {
        IncomesTodayScreen()
    }
}