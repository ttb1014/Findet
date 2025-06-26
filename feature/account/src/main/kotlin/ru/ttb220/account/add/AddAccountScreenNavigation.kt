package ru.ttb220.account.add

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.account.common.TOP_LEVEL_ACCOUNT_ROUTE

const val ADD_ACCOUNT_SCREEN_ROUTE = "$TOP_LEVEL_ACCOUNT_ROUTE/add"

fun NavController.navigateToAddAccount(
    navOptions: NavOptions? = null
) {
    val route = ADD_ACCOUNT_SCREEN_ROUTE
    navigate(route, navOptions)
}

fun NavGraphBuilder.addAccountScreen() {
    composable(
        route = ADD_ACCOUNT_SCREEN_ROUTE,
    ) {
        AddAccountScreenContent()
    }
}