package ru.ttb220.expense.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.expense.di.ExpensesComponentProvider
import ru.ttb220.expense.presentation.ui.ExpensesTodayScreen
import ru.ttb220.expense.presentation.viewmodel.ExpensesTodayViewModel

const val EXPENSES_TODAY_SCREEN_ROUTE_BASE = "$TOP_LEVEL_EXPENSES_ROUTE/today"
const val EXPENSES_TODAY_SCREEN_ROUTE = EXPENSES_TODAY_SCREEN_ROUTE_BASE + "?" +
        "$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToExpensesToday(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = EXPENSES_TODAY_SCREEN_ROUTE_BASE + "?" +
            "$ACTIVE_ACCOUNT_ID=$accountId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.expensesTodayScreen(
    navigateToEditExpense: (Int) -> Unit = {}
) {
    composable(
        route = EXPENSES_TODAY_SCREEN_ROUTE,
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
            (context as ExpensesComponentProvider).provideExpensesComponent().viewModelFactory
        val viewModel = viewModel<ExpensesTodayViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        ExpensesTodayScreen(
            viewModel = viewModel,
            navigateToEditExpense = navigateToEditExpense
        )
    }
}