package ru.ttb220.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.ttb220.account.add.addAccountScreen
import ru.ttb220.account.main.accountScreen
import ru.ttb220.categories.categoriesScreen
import ru.ttb220.expenses.history.expensesHistoryScreen
import ru.ttb220.expenses.today.EXPENSES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.today.expensesTodayScreen
import ru.ttb220.incomes.history.incomesHistoryScreen
import ru.ttb220.incomes.today.incomesTodayScreen
import ru.ttb220.settings.settingsScreen

@Composable
fun FindetNavHost(
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

        accountScreen()
        addAccountScreen()

        categoriesScreen()

        settingsScreen()
    }
}