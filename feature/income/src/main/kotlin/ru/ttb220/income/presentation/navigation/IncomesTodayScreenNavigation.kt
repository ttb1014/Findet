package ru.ttb220.income.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.income.di.IncomesComponentProvider
import ru.ttb220.income.presentation.ui.IncomesTodayScreen
import ru.ttb220.income.presentation.viewmodel.IncomesTodayViewModel

const val INCOMES_TODAY_SCREEN_ROUTE_BASE = "$TOP_LEVEL_INCOMES_ROUTE/today"
const val INCOMES_TODAY_SCREEN_ROUTE = INCOMES_TODAY_SCREEN_ROUTE_BASE + "?" +
        "$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToIncomesToday(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = "$INCOMES_TODAY_SCREEN_ROUTE_BASE?$ACTIVE_ACCOUNT_ID=$accountId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.incomesTodayScreen(
    navigateToEditIncome: (Int) -> Unit,
) {
    composable(
        route = INCOMES_TODAY_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(ACTIVE_ACCOUNT_ID) {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        ),
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as IncomesComponentProvider).provideIncomesComponent().viewModelFactory
        val viewModel = viewModel<IncomesTodayViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        IncomesTodayScreen(
            viewModel = viewModel,
            navigateToEditIncome = navigateToEditIncome
        )
    }
}