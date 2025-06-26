package ru.ttb220.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.ttb220.account.add.ADD_ACCOUNT_SCREEN_ROUTE
import ru.ttb220.account.add.AddAccountViewModel
import ru.ttb220.account.main.navigateToAccount
import ru.ttb220.app.navigation.RouteToTabDataMapper
import ru.ttb220.app.navigation.TopAppBarData
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.expenses.history.EXPENSES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.history.navigateToExpensesHistory
import ru.ttb220.expenses.today.EXPENSES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.today.navigateToExpensesToday
import ru.ttb220.incomes.history.INCOMES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.incomes.history.navigateToIncomesHistory
import ru.ttb220.incomes.today.INCOMES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.incomes.today.navigateToIncomesToday

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
                navHostController.navigateToExpensesToday(
                    activeAccountId
                )

            TopLevelDestination.INCOMES ->
                navHostController.navigateToIncomesToday(
                    activeAccountId
                )

            TopLevelDestination.ACCOUNT ->
                navHostController.navigateToAccount(
                    activeAccountId
                )

            TopLevelDestination.CATEGORIES ->
                navigateTo(TopLevelDestination.CATEGORIES.route)

            TopLevelDestination.SETTINGS ->
                navigateTo(TopLevelDestination.SETTINGS.route)
        }
    }

    fun navigateToHistory(isIncome: Boolean) {
        when (isIncome) {
            true -> navHostController.navigateToIncomesHistory()
            false -> navHostController.navigateToExpensesHistory()
        }
    }

    fun navigateTo(route: String) {
        navHostController.navigate(route)
    }

    @Composable
    fun onTabLeadingIconClick(): () -> Unit {
        if (currentRoute == ADD_ACCOUNT_SCREEN_ROUTE ||
            currentRoute?.contains(EXPENSES_HISTORY_SCREEN_ROUTE_BASE) == true ||
            currentRoute?.contains(INCOMES_HISTORY_SCREEN_ROUTE_BASE) == true
        )
            return { popBackStack() }

        return {}
    }

    @Composable
    fun onTabTrailingIconClick(navBackStackEntry: NavBackStackEntry?): () -> Unit {
        if (currentRoute == ADD_ACCOUNT_SCREEN_ROUTE) {
            val viewModel = navBackStackEntry?.let { entry ->
                val viewModel: AddAccountViewModel = hiltViewModel(entry)
                viewModel
            }

            return {
                viewModel?.onAddAccount()
                popBackStack()
            }
        }

        if (currentRoute?.contains(EXPENSES_TODAY_SCREEN_ROUTE_BASE) == true) {
            return {
                navigateToHistory(false)
            }
        }

        if (currentRoute?.contains(INCOMES_TODAY_SCREEN_ROUTE_BASE) == true) {
            return {
                navigateToHistory(true)
            }
        }

        return { }
    }

    @Composable
    fun topAppBarData(): TopAppBarData? {
        val cachedRoute = currentRoute
        RouteToTabDataMapper.entries.forEach { (route, data) ->
            if (cachedRoute?.contains(route) == true)
                return data
        }

        return null
    }
}