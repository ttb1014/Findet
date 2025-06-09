package ru.ttb220.demo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ttb220.mock.mockExpenseResources
import ru.ttb220.mock.mockTotalExpenses
import ru.ttb220.ui.screen.ExpensesScreen
import ru.ttb220.ui.screen.IncomesScreen

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
                modifier = Modifier
            )
        }
        composable(
            route = Destination.INCOMES.name
        ) {
            IncomesScreen(
                incomesScreenResource = mockIncomesResource,
                modifier = Modifier,
            )
        }
    }
}