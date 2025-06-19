package ru.ttb220.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ADD_ACCOUNT_SCREEN_ROUTE = "account/add"

fun NavController.navigateToAddAccount(
    navOptions: NavOptions? = null
) {
    val route = ADD_ACCOUNT_SCREEN_ROUTE
    navigate(route, navOptions)
}

fun NavGraphBuilder.addAccountScreen(navController: NavController? = null) {
    composable(
        route = ADD_ACCOUNT_SCREEN_ROUTE,
    ) {
        AddAccountScreenContent()
    }
}