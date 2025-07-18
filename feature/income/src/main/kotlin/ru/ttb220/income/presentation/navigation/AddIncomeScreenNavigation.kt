package ru.ttb220.income.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.income.di.IncomesComponentProvider
import ru.ttb220.income.presentation.ui.AddIncomeScreen
import ru.ttb220.income.presentation.viewmodel.AddIncomeViewModel

const val ADD_INCOME_SCREEN_ROUTE_BASE = "$TOP_LEVEL_INCOMES_ROUTE/add"
const val ADD_INCOME_SCREEN_ROUTE = ADD_INCOME_SCREEN_ROUTE_BASE

fun NavController.navigateToAddIncome(
    navOptions: NavOptions? = null
) {
    val route = ADD_INCOME_SCREEN_ROUTE
    navigate(route, navOptions)
}

fun NavGraphBuilder.addIncomeScreen(
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
) {
    composable(
        route = ADD_INCOME_SCREEN_ROUTE,
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as IncomesComponentProvider).provideIncomesComponent().viewModelFactory
        val viewModel = viewModel<AddIncomeViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = factory
        )

        AddIncomeScreen(
            viewModel = viewModel,
            onAccountSelectorLaunch = onAccountSelectorLaunch,
            onCategorySelectorLaunch = onCategorySelectorLaunch,
        )
    }
}