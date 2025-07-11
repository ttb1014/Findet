package ru.ttb220.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.ttb220.account.di.AccountComponentProvider
import ru.ttb220.account.presentation.navigation.ACCOUNT_SCREEN_ROUTE_BASE
import ru.ttb220.account.presentation.navigation.ADD_ACCOUNT_SCREEN_ROUTE
import ru.ttb220.account.presentation.navigation.navigateToAccount
import ru.ttb220.account.presentation.viewmodel.AddAccountViewModel
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.categories.presentation.navigation.navigateToCategories
import ru.ttb220.expenses.presentation.navigation.EXPENSES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.presentation.navigation.EXPENSES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.expenses.presentation.navigation.navigateToExpensesHistory
import ru.ttb220.expenses.presentation.navigation.navigateToExpensesToday
import ru.ttb220.incomes.presentation.navigation.INCOMES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.incomes.presentation.navigation.INCOMES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.incomes.presentation.navigation.navigateToIncomesHistory
import ru.ttb220.incomes.presentation.navigation.navigateToIncomesToday
import ru.ttb220.settings.presentation.navigation.navigateToSettings

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
 * Holds app's data, contains methods for navigation.
 * Navigation is delegated to feature's impl
 */
@Stable
class AppState(
    val activeAccountId: Int?,
    val navHostController: NavHostController,
) {
    // new property.
    // if true we want to hide FAB and bottom navigation
    var isBottomSheetShown by mutableStateOf(false)
    var isTopAppBarIsInEditMode by mutableStateOf(false)

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
        isTopAppBarIsInEditMode = false

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

    // 4 properties of TAB and FAB that are resolved depending on current route
    // ALL values are remembered and functions are called only when active route changes
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
    fun onTabTrailingIconClick(): () -> Unit {
        val cachedRoute = currentRoute

        // return appropriate TAB callback for each possible route
        if (cachedRoute == ADD_ACCOUNT_SCREEN_ROUTE) {
            val context = LocalContext.current.applicationContext
            val factory =
                (context as AccountComponentProvider).provideAccountComponent().viewModelFactory
            val viewModel = viewModel<AddAccountViewModel>(factory = factory)

            return remember(cachedRoute) {
                {
                    viewModel.onAddAccount()
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

        if (cachedRoute?.contains(ACCOUNT_SCREEN_ROUTE_BASE) == true) {
            return remember(cachedRoute) {
                {
                    isTopAppBarIsInEditMode = true
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