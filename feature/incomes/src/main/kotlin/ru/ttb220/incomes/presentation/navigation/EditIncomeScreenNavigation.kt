package ru.ttb220.incomes.presentation.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.ttb220.incomes.di.IncomesComponentProvider
import ru.ttb220.incomes.presentation.ui.EditIncomeScreen
import ru.ttb220.incomes.presentation.viewmodel.EditIncomeViewModel
import ru.ttb220.incomes.presentation.viewmodel.factory.AssistedViewModelFactory

const val EDIT_INCOME_SCREEN_ROUTE_BASE = "$TOP_LEVEL_INCOMES_ROUTE/edit"
const val EDIT_INCOME_SCREEN_ROUTE = EDIT_INCOME_SCREEN_ROUTE_BASE + "?" +
        "$INCOME_ID={$INCOME_ID}"

fun NavController.navigateToEditIncome(
    incomeId: Int,
    navOptions: NavOptions? = null
) {
    val route = EDIT_INCOME_SCREEN_ROUTE_BASE + "?" +
            "$INCOME_ID=$incomeId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.editIncomeScreen(
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    composable(
        route = EDIT_INCOME_SCREEN_ROUTE,
        arguments = listOf(
            navArgument(INCOME_ID) {
                type = NavType.IntType
            },
        ),
    ) { navBackStackEntry ->
        val context = LocalContext.current.applicationContext
        val factory =
            (context as IncomesComponentProvider).provideIncomesComponent().assistedFactory
        val viewModel = viewModel<EditIncomeViewModel>(
            viewModelStoreOwner = navBackStackEntry,
            factory = AssistedViewModelFactory(factory)
        )

        EditIncomeScreen(
            viewModel = viewModel,
            onAccountSelectorLaunch = onAccountSelectorLaunch,
            onCategorySelectorLaunch = onCategorySelectorLaunch,
            onDismiss = onDismiss
        )
    }
}