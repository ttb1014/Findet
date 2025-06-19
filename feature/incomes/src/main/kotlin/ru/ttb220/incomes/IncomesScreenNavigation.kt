package ru.ttb220.incomes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ACTIVE_ACCOUNT_ID = "accountId"
const val INCOMES_SCREEN_ROUTE_BASE = "incomes"
const val INCOMES_SCREEN_ROUTE = "$INCOMES_SCREEN_ROUTE_BASE?$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToIncomes(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = "${INCOMES_SCREEN_ROUTE_BASE}?$ACTIVE_ACCOUNT_ID=$accountId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.incomesScreen() {
    composable(
        route = INCOMES_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(ACTIVE_ACCOUNT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        ),
    ) {
        IncomesScreen()
    }
}