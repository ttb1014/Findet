package ru.ttb220.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.ttb220.account.presentation.navigation.addAccountScreen
import ru.ttb220.account.presentation.navigation.accountScreen
import ru.ttb220.app.ui.AppState
import ru.ttb220.categories.presentation.navigation.categoriesScreen
import ru.ttb220.expenses.presentation.navigation.expensesHistoryScreen
import ru.ttb220.expenses.presentation.navigation.EXPENSES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.presentation.navigation.expensesTodayScreen
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
        expensesTodayScreen()
        expensesHistoryScreen()

        incomesTodayScreen()
        incomesHistoryScreen()

        accountScreen(
            onBottomSheetShow = {
                appState.isBottomSheetShown = true
            },
            onBottomSheetDismiss = {
                appState.isBottomSheetShown = false
            }
        )
        addAccountScreen()

        categoriesScreen()

        settingsScreen()
    }
}