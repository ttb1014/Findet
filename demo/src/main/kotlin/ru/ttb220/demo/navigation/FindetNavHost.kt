package ru.ttb220.demo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ttb220.account.AccountScreenContent
import ru.ttb220.categories.CategoriesScreen
import ru.ttb220.mock.mockAccountScreenData
import ru.ttb220.mock.mockCategoriesScreenContent
import ru.ttb220.mock.mockExpensesScreenData
import ru.ttb220.mock.mockIncomesScreenData
import ru.ttb220.mock.mockSettingsScreenContent
import ru.ttb220.expenses.ExpensesScreenContent
import ru.ttb220.incomes.IncomesScreenContent
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
            ExpensesScreenContent(
                expensesScreenData = mockExpensesScreenData,
            )
        }
        composable(
            route = Destination.INCOMES.name
        ) {
            IncomesScreenContent(
                incomesScreenData = mockIncomesScreenData,
            )
        }
        composable(
            route = Destination.ACCOUNT.name
        ) {
            AccountScreenContent(
                accountScreenData = mockAccountScreenData,
            )
        }
        composable(
            route = Destination.ARTICLES.name
        ) {
            CategoriesScreen(mockCategoriesScreenContent)
        }
        composable(
            route = Destination.SETTINGS.name
        ) {
            SettingsScreen(mockSettingsScreenContent)
        }
    }
}