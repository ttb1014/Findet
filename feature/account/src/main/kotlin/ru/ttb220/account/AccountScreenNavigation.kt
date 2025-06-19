package ru.ttb220.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

const val ACTIVE_ACCOUNT_ID = "accountId"
const val ACCOUNT_SCREEN_ROUTE_BASE = "account"
const val ACCOUNT_SCREEN_ROUTE = "$ACCOUNT_SCREEN_ROUTE_BASE/{$ACTIVE_ACCOUNT_ID}"

fun NavGraphBuilder.accountScreen(navController: NavController) {
    composable(
        route = ACCOUNT_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(ACTIVE_ACCOUNT_ID) {
                this.type = NavType.IntType
                this.nullable = true
            }
        ),
    ) { backStackEntry ->
        // TODO: Можно добавить навигацию на экран со списком доступных счетов на выбор, а пока - игнор

//        val accountId = backStackEntry.arguments?.getInt(ACTIVE_ACCOUNT_ID)
//
//        LaunchedEffect(accountId) {
//            if (accountId == null) {
//                navController.navigate("select_account") {
//                    popUpTo("account") { inclusive = true }
//                }
//            }
//        }

        AccountScreen()
    }
}