package ru.ttb220.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ttb220.account.accountScreen
import ru.ttb220.account.addAccountScreen
import ru.ttb220.categories.CategoriesScreen
import ru.ttb220.expenses.today.expensesScreen
import ru.ttb220.expenses.history.expensesHistoryScreen
import ru.ttb220.incomes.today.incomesScreen
import ru.ttb220.incomes.history.incomesHistoryScreen
import ru.ttb220.mock.mockCategoriesScreenContent
import ru.ttb220.mock.mockSettingsScreenContent
import ru.ttb220.settings.SettingsScreen

@Composable
fun FindetNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startRoute: String = TopLevelDestination.EXPENSES.route
) {
    NavHost(
        navController = navHostController,
        startDestination = startRoute,
        modifier = modifier
    ) {
        expensesScreen()
        incomesScreen()

        accountScreen()
        addAccountScreen()

        expensesHistoryScreen()
        incomesHistoryScreen()

        // 2 экрана остаются моковыми
        composable(
            route = TopLevelDestination.ARTICLES.route
        ) {
            CategoriesScreen(mockCategoriesScreenContent)
        }
        composable(
            route = TopLevelDestination.SETTINGS.route
        ) {
            SettingsScreen(mockSettingsScreenContent)
        }
    }
}