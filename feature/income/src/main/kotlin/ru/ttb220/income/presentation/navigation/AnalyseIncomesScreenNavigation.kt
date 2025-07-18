package ru.ttb220.income.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.income.di.IncomesComponentProvider
import ru.ttb220.income.presentation.ui.IncomesAnalysisScreen
import ru.ttb220.income.presentation.viewmodel.IncomesAnalysisScreenViewModel

const val ANALYSE_INCOME_SCREEN_ROUTE_BASE = "$TOP_LEVEL_INCOMES_ROUTE/analyse"
const val ANALYSE_INCOME_SCREEN_ROUTE = ANALYSE_INCOME_SCREEN_ROUTE_BASE

fun NavController.navigateToAnalyseIncomes(
    navOptions: NavOptions? = null
) {
    val route = ANALYSE_INCOME_SCREEN_ROUTE
    navigate(route, navOptions)
}

fun NavGraphBuilder.analyseIncomeScreen() {
    composable(
        route = ANALYSE_INCOME_SCREEN_ROUTE,
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as IncomesComponentProvider).provideIncomesComponent().viewModelFactory
        val viewModel = viewModel<IncomesAnalysisScreenViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        IncomesAnalysisScreen(
            viewModel = viewModel,
        )
    }
}