package ru.ttb220.account.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.account.di.AccountComponentProvider
import ru.ttb220.account.presentation.ui.AccountScreen
import ru.ttb220.account.presentation.viewmodel.AccountViewModel

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

fun NavGraphBuilder.accountScreen(
    onBottomSheetShow: () -> Unit
) {
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
        val context = LocalContext.current.applicationContext
        val factory =
            (context as AccountComponentProvider).provideAccountComponent().viewModelFactory
        val viewModel = viewModel<AccountViewModel>(factory = factory)

        AccountScreen(
            onBottomSheetShow = onBottomSheetShow,
            viewModel = viewModel
        )
    }
}