package ru.ttb220.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.ttb220.account.presentation.navigation.accountScreen
import ru.ttb220.account.presentation.navigation.addAccountScreen
import ru.ttb220.app.ui.AppState
import ru.ttb220.categories.presentation.navigation.categoriesScreen
import ru.ttb220.expenses.presentation.navigation.EXPENSES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.presentation.navigation.addExpenseScreen
import ru.ttb220.expenses.presentation.navigation.editExpenseScreen
import ru.ttb220.expenses.presentation.navigation.expensesHistoryScreen
import ru.ttb220.expenses.presentation.navigation.expensesTodayScreen
import ru.ttb220.incomes.presentation.navigation.addIncomeScreen
import ru.ttb220.incomes.presentation.navigation.editIncomeScreen
import ru.ttb220.incomes.presentation.navigation.incomesHistoryScreen
import ru.ttb220.incomes.presentation.navigation.incomesTodayScreen
import ru.ttb220.settings.presentation.navigation.settingsScreen

@Composable
fun FindetNavHost(
    appState: AppState,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startRoute: String = EXPENSES_TODAY_SCREEN_ROUTE_BASE
) {
    // adds all available fragments to nav graph
    NavHost(
        navController = navHostController,
        startDestination = startRoute,
        modifier = modifier
    ) {
        expensesTodayScreen(
            navigateToEditExpense = appState::navigateToEditExpense
        )
        expensesHistoryScreen(
            navigateToEditExpense = appState::navigateToEditExpense
        )
        addExpenseScreen(
            onAccountSelectorLaunch = {
                appState.isAccountSelectorShown = true
            },
            onCategorySelectorLaunch = {
                appState.isCategorySelectorShown = true
            }
        )
        editExpenseScreen(
            onAccountSelectorLaunch = {
                appState.isAccountSelectorShown = true
            },
            onCategorySelectorLaunch = {
                appState.isCategorySelectorShown = true
            },
            onDismiss = appState::popBackStack
        )

        incomesTodayScreen(
            navigateToEditIncome = appState::navigateToEditIncome
        )
        incomesHistoryScreen(
            navigateToEditIncome = appState::navigateToEditIncome
        )
        addIncomeScreen(
            onAccountSelectorLaunch = {
                appState.isAccountSelectorShown = true
            },
            onCategorySelectorLaunch = {
                appState.isCategorySelectorShown = true
            }
        )
        editIncomeScreen(
            onAccountSelectorLaunch = {
                appState.isAccountSelectorShown = true
            },
            onCategorySelectorLaunch = {
                appState.isCategorySelectorShown = true
            },
            onDismiss = appState::popBackStack
        )

        accountScreen(
            onBottomSheetShow = {
                appState.isCurrencyBottomSheetShown = true
            }
        )
        addAccountScreen()

        categoriesScreen()

        settingsScreen()
    }
}