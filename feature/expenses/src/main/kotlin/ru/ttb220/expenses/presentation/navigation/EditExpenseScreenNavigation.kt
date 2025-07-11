package ru.ttb220.expenses.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.expenses.di.ExpensesComponentProvider
import ru.ttb220.expenses.presentation.ui.EditExpenseScreen
import ru.ttb220.expenses.presentation.viewmodel.EditExpenseViewModel

const val EDIT_EXPENSE_SCREEN_ROUTE_BASE = "$TOP_LEVEL_EXPENSES_ROUTE/edit"
const val EDIT_EXPENSE_SCREEN_ROUTE = EXPENSES_TODAY_SCREEN_ROUTE_BASE + "?" +
        "$EXPENSE_ID={$EXPENSE_ID}"

fun NavController.navigateToEditExpense(
    expenseId: Int,
    navOptions: NavOptions? = null
) {
    val route = EDIT_EXPENSE_SCREEN_ROUTE_BASE + "?" +
            "$EXPENSE_ID=$expenseId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.editExpenseScreen() {
    composable(
        route = EDIT_EXPENSE_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(EXPENSE_ID) {
                type = NavType.IntType
            },
        ),
    ) {
        val context = LocalContext.current.applicationContext
        val factory =
            (context as ExpensesComponentProvider).provideExpensesComponent().viewModelFactory
        val viewModel = viewModel<EditExpenseViewModel>(factory = factory)

        EditExpenseScreen(viewModel = viewModel)
    }
}