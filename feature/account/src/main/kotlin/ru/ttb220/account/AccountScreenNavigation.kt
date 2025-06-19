package ru.ttb220.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ACTIVE_ACCOUNT_ID = "accountId"
const val ACCOUNT_SCREEN_ROUTE_BASE = "account"
const val ACCOUNT_SCREEN_ROUTE = "$ACCOUNT_SCREEN_ROUTE_BASE?$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToAccount(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = "${ACCOUNT_SCREEN_ROUTE_BASE}?$ACTIVE_ACCOUNT_ID=$accountId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.accountScreen(navController: NavController? = null) {
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