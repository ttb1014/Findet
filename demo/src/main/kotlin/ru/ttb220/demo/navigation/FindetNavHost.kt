package ru.ttb220.demo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ttb220.account.AccountScreenContent
import ru.ttb220.categories.CategoriesScreen
import ru.ttb220.mock.mockAccountScreenContent
import ru.ttb220.mock.mockArticleScreenContent
import ru.ttb220.mock.mockExpensesScreenContent
import ru.ttb220.mock.mockIncomesScreenContent
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
                expensesScreenContent = mockExpensesScreenContent,
            )
        }
        composable(
            route = Destination.INCOMES.name
        ) {
            IncomesScreenContent(
                incomesScreenContent = mockIncomesScreenContent,
            )
        }
        composable(
            route = Destination.ACCOUNT.name
        ) {
            AccountScreenContent(
                accountScreenContent = mockAccountScreenContent,
            )
        }
        composable(
            route = Destination.ARTICLES.name
        ) {
            CategoriesScreen(mockArticleScreenContent)
        }
        composable(
            route = Destination.SETTINGS.name
        ) {
            SettingsScreen(mockSettingsScreenContent)
        }
    }
}