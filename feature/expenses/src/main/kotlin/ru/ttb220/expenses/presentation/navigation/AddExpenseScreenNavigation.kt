package ru.ttb220.expenses.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.expenses.di.ExpensesComponentProvider
import ru.ttb220.expenses.presentation.ui.AddExpenseScreen
import ru.ttb220.expenses.presentation.viewmodel.AddExpenseViewModel

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
    ) {
        val context = LocalContext.current.applicationContext
        val factory =
            (context as ExpensesComponentProvider).provideExpensesComponent().viewModelFactory
        val viewModel = viewModel<AddExpenseViewModel>(factory = factory)

        AddExpenseScreen(
            viewModel = viewModel,
            onAccountSelectorLaunch = onAccountSelectorLaunch,
            onCategorySelectorLaunch = onCategorySelectorLaunch,
        )
    }
}