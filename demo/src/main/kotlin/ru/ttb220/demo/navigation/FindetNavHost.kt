package ru.ttb220.demo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ttb220.account.presentation.ui.AccountScreenContent
import ru.ttb220.categories.presentation.ui.CategoriesScreen
import ru.ttb220.mock.mockAccountScreenData
import ru.ttb220.mock.mockCategoriesScreenContent
import ru.ttb220.mock.mockExpensesScreenData
import ru.ttb220.mock.mockIncomesScreenData
import ru.ttb220.mock.mockSettingsScreenContent
import ru.ttb220.expenses.presentation.ui.ExpensesTodayScreenContent
import ru.ttb220.incomes.presentation.ui.IncomesTodayScreenContent
import ru.ttb220.settings.presentation.ui.SettingsScreen

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
            ExpensesTodayScreenContent(
                expensesScreenData = mockExpensesScreenData,
            )
        }
        composable(
            route = Destination.INCOMES.name
        ) {
            IncomesTodayScreenContent(
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