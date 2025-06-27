package ru.ttb220.account.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.account.common.ACTIVE_ACCOUNT_ID
import ru.ttb220.account.common.TOP_LEVEL_ACCOUNT_ROUTE

const val ACCOUNT_SCREEN_ROUTE_BASE = TOP_LEVEL_ACCOUNT_ROUTE + "/" + "main"
const val ACCOUNT_SCREEN_ROUTE =
    "$ACCOUNT_SCREEN_ROUTE_BASE?$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToAccount(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = "$ACCOUNT_SCREEN_ROUTE_BASE?$ACTIVE_ACCOUNT_ID=$accountId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.accountScreen() {
    composable(
        route = ACCOUNT_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(ACTIVE_ACCOUNT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        ),
    ) {
        AccountScreen()
    }
}