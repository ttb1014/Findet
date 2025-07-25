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
import ru.ttb220.expense.presentation.ui.ExpensesHistoryScreen
import ru.ttb220.expense.presentation.viewmodel.ExpensesHistoryViewModel

const val EXPENSES_HISTORY_SCREEN_ROUTE_BASE = "$TOP_LEVEL_EXPENSES_ROUTE/history"
const val EXPENSES_HISTORY_SCREEN_ROUTE = EXPENSES_HISTORY_SCREEN_ROUTE_BASE + "?" +
        "$ACTIVE_ACCOUNT_ID={$ACTIVE_ACCOUNT_ID}"

fun NavController.navigateToExpensesHistory(
    accountId: Int? = null,
    navOptions: NavOptions? = null
) {
    val route = EXPENSES_HISTORY_SCREEN_ROUTE_BASE + "?" +
            "$ACTIVE_ACCOUNT_ID=$accountId"

    navigate(route, navOptions)
}

fun NavGraphBuilder.expensesHistoryScreen(
    navigateToEditExpense: (Int) -> Unit = {},
) {
    composable(
        route = EXPENSES_HISTORY_SCREEN_ROUTE,
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
        val viewModel = viewModel<ExpensesHistoryViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        ExpensesHistoryScreen(
            viewModel = viewModel,
            navigateToEditExpense = navigateToEditExpense
        )
    }
}