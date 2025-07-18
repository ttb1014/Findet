package ru.ttb220.expense.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.expense.di.ExpensesComponentProvider
import ru.ttb220.expense.presentation.ui.AddExpenseScreen
import ru.ttb220.expense.presentation.viewmodel.AddExpenseViewModel

const val ADD_EXPENSE_SCREEN_ROUTE_BASE = "$TOP_LEVEL_EXPENSES_ROUTE/add"
const val ADD_EXPENSE_SCREEN_ROUTE = ADD_EXPENSE_SCREEN_ROUTE_BASE

fun NavController.navigateToAddExpense(
    navOptions: NavOptions? = null
) {
    val route = ADD_EXPENSE_SCREEN_ROUTE
    navigate(route, navOptions)
}

fun NavGraphBuilder.addExpenseScreen(
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
) {
    composable(
        route = ADD_EXPENSE_SCREEN_ROUTE,
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as ExpensesComponentProvider).provideExpensesComponent().viewModelFactory
        val viewModel = viewModel<AddExpenseViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        AddExpenseScreen(
            viewModel = viewModel,
            onAccountSelectorLaunch = onAccountSelectorLaunch,
            onCategorySelectorLaunch = onCategorySelectorLaunch,
        )
    }
}