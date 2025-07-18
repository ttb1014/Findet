package ru.ttb220.findet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.ttb220.account.presentation.navigation.ACCOUNT_SCREEN_ROUTE_BASE
import ru.ttb220.account.presentation.navigation.accountScreen
import ru.ttb220.account.presentation.navigation.addAccountScreen
import ru.ttb220.category.presentation.navigation.categoriesScreen
import ru.ttb220.expense.presentation.navigation.addExpenseScreen
import ru.ttb220.expense.presentation.navigation.analyseExpenseScreen
import ru.ttb220.expense.presentation.navigation.editExpenseScreen
import ru.ttb220.expense.presentation.navigation.expensesHistoryScreen
import ru.ttb220.expense.presentation.navigation.expensesTodayScreen
import ru.ttb220.findet.ui.AppState
import ru.ttb220.income.presentation.navigation.addIncomeScreen
import ru.ttb220.income.presentation.navigation.analyseIncomeScreen
import ru.ttb220.income.presentation.navigation.editIncomeScreen
import ru.ttb220.income.presentation.navigation.incomesHistoryScreen
import ru.ttb220.income.presentation.navigation.incomesTodayScreen
import ru.ttb220.setting.presentation.navigation.settingsScreen

@Composable
fun FindetNavHost(
    appState: AppState,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startRoute: String = ACCOUNT_SCREEN_ROUTE_BASE
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
        analyseExpenseScreen()

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
        analyseIncomeScreen()

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