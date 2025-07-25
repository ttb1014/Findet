package ru.ttb220.findet.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.map
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.account.di.AccountComponentProvider
import ru.ttb220.account.presentation.navigation.ACCOUNT_SCREEN_ROUTE
import ru.ttb220.account.presentation.navigation.ACCOUNT_SCREEN_ROUTE_BASE
import ru.ttb220.account.presentation.navigation.ADD_ACCOUNT_SCREEN_ROUTE
import ru.ttb220.account.presentation.navigation.navigateToAccount
import ru.ttb220.account.presentation.navigation.navigateToAddAccount
import ru.ttb220.account.presentation.viewmodel.AddAccountViewModel
import ru.ttb220.category.presentation.navigation.navigateToCategories
import ru.ttb220.data.api.NetworkMonitor
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.sync.SyncManager
import ru.ttb220.expense.di.ExpensesComponentProvider
import ru.ttb220.expense.presentation.navigation.ADD_EXPENSE_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.ANALYSE_EXPENSE_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.EDIT_EXPENSE_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.EXPENSES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.EXPENSES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.navigateToAddExpense
import ru.ttb220.expense.presentation.navigation.navigateToAnalyseExpenses
import ru.ttb220.expense.presentation.navigation.navigateToEditExpense
import ru.ttb220.expense.presentation.navigation.navigateToExpensesHistory
import ru.ttb220.expense.presentation.navigation.navigateToExpensesToday
import ru.ttb220.expense.presentation.viewmodel.AddExpenseViewModel
import ru.ttb220.expense.presentation.viewmodel.EditExpenseViewModel
import ru.ttb220.expense.presentation.viewmodel.factory.AssistedViewModelFactory
import ru.ttb220.findet.navigation.TopLevelDestination
import ru.ttb220.findet.util.routeToTabDataMapper
import ru.ttb220.income.di.IncomesComponentProvider
import ru.ttb220.income.presentation.navigation.ADD_INCOME_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.ANALYSE_INCOME_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.EDIT_INCOME_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.INCOMES_HISTORY_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.INCOMES_TODAY_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.navigateToAddIncome
import ru.ttb220.income.presentation.navigation.navigateToAnalyseIncomes
import ru.ttb220.income.presentation.navigation.navigateToEditIncome
import ru.ttb220.income.presentation.navigation.navigateToIncomesHistory
import ru.ttb220.income.presentation.navigation.navigateToIncomesToday
import ru.ttb220.income.presentation.viewmodel.AddIncomeViewModel
import ru.ttb220.income.presentation.viewmodel.EditIncomeViewModel
import ru.ttb220.setting.presentation.navigation.navigateToSettingsMain

@Composable
fun rememberAppState(
    activeAccountId: Int? = null,
    navController: NavHostController = rememberNavController(),
    networkMonitor: NetworkMonitor,
    syncManager: SyncManager,
    settingsRepository: SettingsRepository,
    timeZone: TimeZone,
) = remember {
    AppState(
        activeAccountId = activeAccountId,
        navHostController = navController,
        networkMonitor = networkMonitor,
        syncManager = syncManager,
        settingsRepository = settingsRepository,
        timeZone = timeZone,
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
    val networkMonitor: NetworkMonitor,
    val syncManager: SyncManager,
    val settingsRepository: SettingsRepository,
    val timeZone: TimeZone
) {
    var isAccountSelectorShown: Boolean by mutableStateOf(false)
    var isCategorySelectorShown: Boolean by mutableStateOf(false)
    var isCurrencyBottomSheetShown by mutableStateOf(false)
    var isTopAppBarIsInEditMode by mutableStateOf(false)

    val isConnected = networkMonitor.isOnline

    val lastSyncTimeFormatted = syncManager.lastSyncTimeFlow.map {
        it.toLocalDateTime(timeZone).date.toString()
    }

    val shouldPlayHapticsFlow = settingsRepository.getHapticsEnabledFlow()

    private fun hideBottomSheets() {
        isAccountSelectorShown = false
        isCategorySelectorShown = false
        isCurrencyBottomSheetShown = false
    }

    val isPinSetup = settingsRepository.isPinSetup()

    val currentBackStackEntry: NavBackStackEntry?
        @Composable get() = navHostController.currentBackStackEntryAsState().value

    val currentRoute: String?
        @Composable get() = currentBackStackEntry?.destination?.route

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() {
            return currentRoute?.let { route ->
                TopLevelDestination.entries.firstOrNull { topLevelDestination ->
                    route.contains(topLevelDestination.route)
                }
            }
        }

    fun popBackStack() {
        hideBottomSheets()
        navHostController.popBackStack()
    }

    fun authorizeAndNavigateToAccount() {
        navHostController.navigateToAccount(
            activeAccountId
        )

        navHostController.popBackStack(
            route = ACCOUNT_SCREEN_ROUTE,
            inclusive = false
        )
    }

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
                navHostController.navigateToSettingsMain()
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

    fun navigateToEditExpense(expenseId: Int) =
        navHostController.navigateToEditExpense(expenseId)

    fun navigateToEditIncome(incomeId: Int) =
        navHostController.navigateToEditIncome(incomeId)

    // 4 properties of TAB and FAB that are resolved depending on current route
    // ALL values are remembered and functions are called only when active route changes
    @Composable
    fun onTabLeadingIconClick(): () -> Unit {
        val cachedRoute = currentRoute

        // return appropriate TAB callback for each possible route
        if (cachedRoute == ADD_ACCOUNT_SCREEN_ROUTE ||
            cachedRoute?.contains(EXPENSES_HISTORY_SCREEN_ROUTE_BASE) == true ||
            cachedRoute?.contains(INCOMES_HISTORY_SCREEN_ROUTE_BASE) == true ||
            cachedRoute?.contains(ADD_INCOME_SCREEN_ROUTE_BASE) == true ||
            cachedRoute?.contains(ADD_EXPENSE_SCREEN_ROUTE_BASE) == true ||
            cachedRoute?.contains(EDIT_EXPENSE_SCREEN_ROUTE_BASE) == true ||
            cachedRoute?.contains(EDIT_INCOME_SCREEN_ROUTE_BASE) == true ||
            cachedRoute?.contains(ANALYSE_EXPENSE_SCREEN_ROUTE_BASE) == true ||
            cachedRoute?.contains(ANALYSE_INCOME_SCREEN_ROUTE_BASE) == true
        )
            return remember(cachedRoute) { { popBackStack() } }

        return remember(cachedRoute) { {} }
    }

    @Composable
    fun onTabTrailingIconClick(): () -> Unit {
        val cachedNavEntry = currentBackStackEntry
        val cachedRoute = currentRoute

        // return appropriate TAB callback for each possible route
        if (cachedRoute == ADD_ACCOUNT_SCREEN_ROUTE) {
            val context = LocalContext.current.applicationContext
            val factory =
                (context as AccountComponentProvider).provideAccountComponent().viewModelFactory
            val viewModel = viewModel<AddAccountViewModel>(
                viewModelStoreOwner = cachedNavEntry!!,
                factory = factory
            )

            return remember(cachedRoute) {
                {
                    viewModel.onAddAccount()
                    popBackStack()
                }
            }
        }

        if (cachedRoute == ADD_INCOME_SCREEN_ROUTE_BASE) {
            val context = LocalContext.current.applicationContext
            val factory =
                (context as IncomesComponentProvider).provideIncomesComponent().viewModelFactory
            val viewModel = viewModel<AddIncomeViewModel>(
                viewModelStoreOwner = cachedNavEntry!!,
                factory = factory
            )

            return remember(cachedRoute) {
                {
                    viewModel.onAddIncome()
                    popBackStack()
                }
            }
        }

        if (cachedRoute == ADD_EXPENSE_SCREEN_ROUTE_BASE) {
            val context = LocalContext.current.applicationContext
            val factory =
                (context as ExpensesComponentProvider).provideExpensesComponent().viewModelFactory
            val viewModel = viewModel<AddExpenseViewModel>(
                viewModelStoreOwner = cachedNavEntry!!,
                factory = factory
            )

            return remember(cachedRoute) {
                {
                    viewModel.onAddExpense()
                    popBackStack()
                }
            }
        }

        if (cachedRoute?.contains(EDIT_EXPENSE_SCREEN_ROUTE_BASE) == true) {
            val context = LocalContext.current.applicationContext
            val factory =
                (context as ExpensesComponentProvider).provideExpensesComponent().assistedFactory
            val editExpenseViewModel = viewModel<EditExpenseViewModel>(
                viewModelStoreOwner = cachedNavEntry!!,
                factory = AssistedViewModelFactory(
                    factory
                )
            )
            return remember(cachedRoute) {
                {
                    editExpenseViewModel.onEditExpense()
                    popBackStack()
                }
            }
        }

        if (cachedRoute?.contains(EDIT_INCOME_SCREEN_ROUTE_BASE) == true) {
            val context = LocalContext.current.applicationContext
            val factory =
                (context as IncomesComponentProvider).provideIncomesComponent().assistedFactory
            val editExpenseViewModel = viewModel<EditIncomeViewModel>(
                viewModelStoreOwner = cachedNavEntry!!,
                factory = ru.ttb220.income.presentation.viewmodel.factory.AssistedViewModelFactory(
                    factory
                )
            )
            return remember(cachedRoute) {
                {
                    editExpenseViewModel.onEditIncome()
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

        if (cachedRoute?.contains(EXPENSES_HISTORY_SCREEN_ROUTE_BASE) == true) {
            return remember(cachedRoute) {
                { navHostController.navigateToAnalyseExpenses() }
            }
        }

        if (cachedRoute?.contains(INCOMES_HISTORY_SCREEN_ROUTE_BASE) == true) {
            return remember(cachedRoute) {
                { navHostController.navigateToAnalyseIncomes() }
            }
        }

        return remember(cachedRoute) { { } }
    }

    @Composable
    fun topAppBarData(): TopAppBarData? {
        val cachedRoute = currentRoute

        // if mapper contains information of visuals for route -> remember and return
        routeToTabDataMapper().entries.forEach { (route, data) ->
            if (cachedRoute?.contains(route) == true)
                return remember(cachedRoute, MaterialTheme.colorScheme) { data }
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
                TopLevelDestination.INCOMES -> {
                    {
                        navHostController.navigateToAddIncome()
                    }
                }

                TopLevelDestination.EXPENSES -> {
                    {
                        navHostController.navigateToAddExpense()
                    }
                }

                TopLevelDestination.ACCOUNT -> {
                    {
                        navHostController.navigateToAddAccount()
                    }
                }

                else -> {
                    {}
                }
            }
        }
    }
}