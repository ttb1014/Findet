package ru.ttb220.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.ttb220.account.navigateToAccount
import ru.ttb220.account.navigateToAddAccount
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.expenses.navigateToExpenses
import ru.ttb220.expenses_history.navigateToExpensesHistory
import ru.ttb220.incomes.navigateToIncomes
import ru.ttb220.incomes_history.navigateToIncomesHistory

@Composable
fun rememberAppState(
    activeAccountId: Int? = null,
    navController: NavHostController = rememberNavController()
) = remember {
    AppState(
        activeAccountId,
        navController,
    )
}

@Stable
class AppState(
    val activeAccountId: Int?,
    val navHostController: NavHostController,
) {
    val currentRoute: String?
        @Composable get() = navHostController.currentBackStackEntryAsState().value?.destination?.route

    // Текущий активный экран
    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return currentRoute?.let { route ->
                TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                    route.contains(topLevelDestination.route)
                }
            }
        }

    fun popBackStack() = navHostController.popBackStack()

    fun navigateTo(topLevelDestination: TopLevelDestination) {
        when (topLevelDestination) {
            TopLevelDestination.EXPENSES ->
                navHostController.navigateToExpenses(
                    activeAccountId
                )

            TopLevelDestination.INCOMES ->
                navHostController.navigateToIncomes(
                    activeAccountId
                )

            TopLevelDestination.ACCOUNT ->
                navHostController.navigateToAccount(
                    activeAccountId
                )

            TopLevelDestination.ARTICLES ->
                navigateTo(TopLevelDestination.ARTICLES.route)

            TopLevelDestination.SETTINGS ->
                navigateTo(TopLevelDestination.SETTINGS.route)

            TopLevelDestination.INCOMES_HISTORY ->
                navHostController.navigateToIncomesHistory()

            TopLevelDestination.EXPENSES_HISTORY ->
                navHostController.navigateToExpensesHistory()

            TopLevelDestination.ADD_ACCOUNT ->
                navHostController.navigateToAddAccount()
        }
    }

    fun navigateToHistory(isIncome: Boolean) {
        when (isIncome) {
            true -> navHostController.navigateToIncomesHistory()
            false -> navHostController.navigateToExpensesHistory()
        }
    }

    private fun navigateTo(destination: String) {
        navHostController.navigate(destination)
    }
}