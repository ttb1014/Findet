package ru.ttb220.demo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ttb220.account.AccountScreen
import ru.ttb220.categories.CategoriesScreen
import ru.ttb220.mock.mockAccountScreenState
import ru.ttb220.mock.mockArticleScreenState
import ru.ttb220.mock.mockExpensesScreenState
import ru.ttb220.mock.mockIncomesScreenState
import ru.ttb220.mock.mockSettingsScreenState
import ru.ttb220.expenses.ExpensesScreen
import ru.ttb220.incomes.IncomesScreen
import ru.ttb220.settings.SettingsScreen

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
                expensesScreenState = mockExpensesScreenState,
            )
        }
        composable(
            route = Destination.INCOMES.name
        ) {
            IncomesScreen(
                incomesScreenState = mockIncomesScreenState,
            )
        }
        composable(
            route = Destination.ACCOUNT.name
        ) {
            AccountScreen(
                accountScreenState = mockAccountScreenState,
            )
        }
        composable(
            route = Destination.ARTICLES.name
        ) {
            CategoriesScreen(mockArticleScreenState)
        }
        composable(
            route = Destination.SETTINGS.name
        ) {
            SettingsScreen(mockSettingsScreenState)
        }
    }
}