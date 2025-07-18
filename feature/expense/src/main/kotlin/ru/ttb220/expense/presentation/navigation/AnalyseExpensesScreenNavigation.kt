package ru.ttb220.expense.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.expense.di.ExpensesComponentProvider
import ru.ttb220.expense.presentation.ui.ExpensesAnalysisScreen
import ru.ttb220.expense.presentation.viewmodel.ExpensesAnalysisScreenViewModel

const val ANALYSE_EXPENSE_SCREEN_ROUTE_BASE = "$TOP_LEVEL_EXPENSES_ROUTE/analyse"
const val ANALYSE_EXPENSE_SCREEN_ROUTE = ANALYSE_EXPENSE_SCREEN_ROUTE_BASE

fun NavController.navigateToAnalyseExpenses(
    navOptions: NavOptions? = null
) {
    val route = ANALYSE_EXPENSE_SCREEN_ROUTE
    navigate(route, navOptions)
}

fun NavGraphBuilder.analyseExpenseScreen() {
    composable(
        route = ANALYSE_EXPENSE_SCREEN_ROUTE,
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as ExpensesComponentProvider).provideExpensesComponent().viewModelFactory
        val viewModel = viewModel<ExpensesAnalysisScreenViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        ExpensesAnalysisScreen(
            viewModel = viewModel,
        )
    }
}