package ru.ttb220.demo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.ttb220.mock.mockExpenseResources
import ru.ttb220.mock.mockTotalExpenses
import ru.ttb220.ui.component.ExpensesList
import ru.ttb220.ui.model.TopLevelDestination

@Composable
fun FindetNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = TopLevelDestination.EXPENSES.name
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(
            route = TopLevelDestination.EXPENSES.name
        ) {
            ExpensesList(
                expenses = mockExpenseResources,
                expensesTotal = mockTotalExpenses,
                modifier = Modifier
            )
        }
    }
}