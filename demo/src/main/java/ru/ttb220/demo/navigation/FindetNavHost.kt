package ru.ttb220.demo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ttb220.mock.mockAccountScreenResource
import ru.ttb220.mock.mockArticleScreenResource
import ru.ttb220.mock.mockExpenseResources
import ru.ttb220.mock.mockIncomesScreenResource
import ru.ttb220.mock.mockSettingsScreenResource
import ru.ttb220.mock.mockTotalExpenses
import ru.ttb220.ui.screen.AccountScreen
import ru.ttb220.ui.screen.ArticlesScreen
import ru.ttb220.ui.screen.ExpensesScreen
import ru.ttb220.ui.screen.IncomesScreen
import ru.ttb220.ui.screen.SettingsScreen

@Composable
fun FindetNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startRoute: String = Destination.EXPENSES.name
) {
    NavHost(
        navController = navHostController,
        startDestination = startRoute,
        modifier = modifier
    ) {
        composable(
            route = Destination.EXPENSES.name
        ) {
            ExpensesScreen(
                expenses = mockExpenseResources,
                expensesTotal = mockTotalExpenses,
            )
        }
        composable(
            route = Destination.INCOMES.name
        ) {
            IncomesScreen(
                incomesScreenResource = mockIncomesScreenResource,
            )
        }
        composable(
            route = Destination.ACCOUNT.name
        ) {
            AccountScreen(
                accountScreenResource = mockAccountScreenResource,
            )
        }
        composable(
            route = Destination.ARTICLES.name
        ) {
            ArticlesScreen(mockArticleScreenResource)
        }
        composable(
            route = Destination.SETTINGS.name
        ) {
            SettingsScreen(mockSettingsScreenResource)
        }
    }
}