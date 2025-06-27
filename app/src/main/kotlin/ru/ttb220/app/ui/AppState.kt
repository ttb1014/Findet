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
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.categories.navigateToCategories
import ru.ttb220.expenses.history.EXPENSES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.history.navigateToExpensesHistory
import ru.ttb220.expenses.today.EXPENSES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.today.navigateToExpensesToday
import ru.ttb220.incomes.history.INCOMES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.incomes.history.navigateToIncomesHistory
import ru.ttb220.incomes.today.INCOMES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.incomes.today.navigateToIncomesToday
import ru.ttb220.settings.navigateToSettings

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

/**
 * holds app's data, contains methods for navigation.
 * Probably we want to move navigation logic to viewModel, but that would only overcomplicate code, since navigation
 * is delegated to feature's implementation
 */
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
                navHostController.navigateToCategories()

            TopLevelDestination.SETTINGS ->
                navHostController.navigateToSettings()
        }
    }

    private fun navigateToHistory(isIncome: Boolean) {
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
        val cachedRoute = currentRoute

        // return appropriate TAB callback for each possible route
        if (cachedRoute == ADD_ACCOUNT_SCREEN_ROUTE ||
            cachedRoute?.contains(EXPENSES_HISTORY_SCREEN_ROUTE_BASE) == true ||
            cachedRoute?.contains(INCOMES_HISTORY_SCREEN_ROUTE_BASE) == true
        )
            return remember(cachedRoute) { { popBackStack() } }

        return remember(cachedRoute) { {} }
    }

    @Composable
    fun onTabTrailingIconClick(navBackStackEntry: NavBackStackEntry?): () -> Unit {
        val cachedRoute = currentRoute

        // return appropriate TAB callback for each possible route
        if (cachedRoute == ADD_ACCOUNT_SCREEN_ROUTE) {
            val viewModel = navBackStackEntry?.let { entry ->
                val viewModel: AddAccountViewModel = hiltViewModel(entry)
                viewModel
            }

            return remember(cachedRoute) {
                {
                    viewModel?.onAddAccount()
                    popBackStack()
                }
            }
        }

        if (cachedRoute?.contains(EXPENSES_TODAY_SCREEN_ROUTE_BASE) == true) {
            return remember(cachedRoute) {
                {
                    navigateToHistory(false)
                }
            }
        }

        if (cachedRoute?.contains(INCOMES_TODAY_SCREEN_ROUTE_BASE) == true) {
            return remember(cachedRoute) {
                {
                    navigateToHistory(true)
                }
            }
        }

        return remember(cachedRoute) { { } }
    }

    @Composable
    fun topAppBarData(): TopAppBarData? {
        val cachedRoute = currentRoute

        // if mapper contains information of visuals for route -> remember and return
        RouteToTabDataMapper.entries.forEach { (route, data) ->
            if (cachedRoute?.contains(route) == true)
                return remember(cachedRoute) { data }
        }

        // while splash screen is shown, currentRoute is null
        return remember(cachedRoute) { null }
    }

    // same as top bar callbacks
    @Composable
    fun fabOnClick(): () -> Unit {
        val cachedTopLevelDestination = currentTopLevelDestination

        return remember(cachedTopLevelDestination) {
            when (cachedTopLevelDestination) {
                TopLevelDestination.ACCOUNT -> {
                    {
                        navigateTo(ADD_ACCOUNT_SCREEN_ROUTE)
                    }
                }

                else -> {
                    {}
                }
            }
        }
    }
}