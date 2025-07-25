package ru.ttb220.findet.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.ttb220.account.di.AccountComponentProvider
import ru.ttb220.account.presentation.ui.AccountEditableTopAppBar
import ru.ttb220.account.presentation.viewmodel.AccountViewModel
import ru.ttb220.bottomsheet.presentation.model.CategoryBottomSheetType
import ru.ttb220.bottomsheet.presentation.ui.AccountBottomSheet
import ru.ttb220.bottomsheet.presentation.ui.CategoryBottomSheet
import ru.ttb220.bottomsheet.presentation.ui.CurrencyBottomSheet
import ru.ttb220.bottomsheet.presentation.viewmodel.AccountBottomSheetViewModel
import ru.ttb220.bottomsheet.presentation.viewmodel.CategoryBottomSheetViewModel
import ru.ttb220.bottomsheet.presentation.viewmodel.CurrencyViewModel
import ru.ttb220.designsystem.component.AddFab
import ru.ttb220.designsystem.topappbar.TopAppBar
import ru.ttb220.designsystem.util.scrim
import ru.ttb220.expense.di.ExpensesComponentProvider
import ru.ttb220.expense.presentation.navigation.ADD_EXPENSE_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.EDIT_EXPENSE_SCREEN_ROUTE_BASE
import ru.ttb220.expense.presentation.navigation.TOP_LEVEL_EXPENSES_ROUTE
import ru.ttb220.expense.presentation.viewmodel.AddExpenseViewModel
import ru.ttb220.expense.presentation.viewmodel.EditExpenseViewModel
import ru.ttb220.expense.presentation.viewmodel.factory.AssistedViewModelFactory
import ru.ttb220.findet.navigation.FabRoutes
import ru.ttb220.findet.navigation.FindetNavHost
import ru.ttb220.findet.navigation.TopLevelDestination
import ru.ttb220.income.di.IncomesComponentProvider
import ru.ttb220.income.presentation.navigation.ADD_INCOME_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.EDIT_INCOME_SCREEN_ROUTE_BASE
import ru.ttb220.income.presentation.navigation.TOP_LEVEL_INCOMES_ROUTE
import ru.ttb220.income.presentation.viewmodel.AddIncomeViewModel
import ru.ttb220.income.presentation.viewmodel.EditIncomeViewModel

@Composable
fun FindetApp(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    val currentRoute = appState.currentRoute
    val currentTopLevelDestination = appState.currentTopLevelDestination
    val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()

    // callback resolution is delegated to appState.
    // a callback from viewModel instance
    val onTrailingIconClick: () -> Unit = appState.onTabTrailingIconClick()
    val onLeadingIconClick: () -> Unit = appState.onTabLeadingIconClick()
    val fabOnClick: () -> Unit = appState.fabOnClick()

    // topAppBar visuals vary depending on currentRoute
    val topAppBarData = appState.topAppBarData()

    val isConnected by appState.isConnected.collectAsStateWithLifecycle(false)
    val lastSyncTimeFormatted by appState.lastSyncTimeFormatted.collectAsStateWithLifecycle("")

    val haptic = LocalHapticFeedback.current
    val shouldPlayHaptic by appState.shouldPlayHapticsFlow.collectAsStateWithLifecycle(false)

    Scaffold(
        modifier = modifier,
        topBar = tab@{
            topAppBarData?.let {

                val defaultText = stringResource(topAppBarData.textId)

                if (appState.isTopAppBarIsInEditMode) {
                    val context = (LocalContext.current.applicationContext)
                    val factory =
                        (context as AccountComponentProvider).provideAccountComponent().viewModelFactory
                    val viewModel = viewModel<AccountViewModel>(factory = factory)

                    AccountEditableTopAppBar(
                        modifier = Modifier.let {
                            if (
                                appState.isCurrencyBottomSheetShown
                                || appState.isAccountSelectorShown
                                || appState.isCategorySelectorShown
                            ) {
                                val scrim = MaterialTheme.colorScheme.scrim

                                it.scrim(scrim)
                            } else
                                it
                        },
                        accountViewModel = viewModel,
                        hideTabCallback = {
                            appState.isTopAppBarIsInEditMode = false
                        },
                        lastSyncFormattedTime = lastSyncTimeFormatted,
                        isConnected = isConnected,
                    )
                    return@tab
                }

                TopAppBar(
                    text = defaultText,
                    leadingIcon = topAppBarData.leadingIconId,
                    trailingIcon = topAppBarData.trailingIconId,
                    onLeadingIconClick = onLeadingIconClick,
                    onTrailingIconClick = onTrailingIconClick,
                    modifier = Modifier
                        .let {
                            if (appState.isCurrencyBottomSheetShown
                                || appState.isAccountSelectorShown
                                || appState.isCategorySelectorShown
                            ) {
                                val scrim = MaterialTheme.colorScheme.scrim

                                it.scrim(scrim)
                            } else
                                it
                        },
                    lastSyncFormattedTime = lastSyncTimeFormatted,
                    isConnected = isConnected,
                    color = topAppBarData.backgroundColor
                )
            }
        },
        bottomBar = bottomBar@{
            if (appState.isCurrencyBottomSheetShown.not()) {
                BottomBar(
                    destinations = TopLevelDestination.entries,
                    currentTopLevelDestination = currentTopLevelDestination,
                    onNavigateTo = {
                        if (shouldPlayHaptic) {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                        }

                        if (currentTopLevelDestination != it)
                            appState.navigateTo(it)
                    }
                )
                return@bottomBar
            }
        },
        floatingActionButton = {
            if (FabRoutes.any { currentRoute?.contains(it) == true } && appState.isCurrencyBottomSheetShown.not())
                AddFab(
                    onClick = fabOnClick
                )
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.surface,
    ) { padding ->
        FindetNavHost(
            appState = appState,
            navHostController = appState.navHostController,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                )
                .let {
                    if (
                        appState.isCurrencyBottomSheetShown
                        || appState.isAccountSelectorShown
                        || appState.isCategorySelectorShown
                    )
                        it.scrim(
                            MaterialTheme.colorScheme.scrim,
                        )
                    else
                        it
                },
        )
    }

    // TODO: change to state machine
    if (appState.isCurrencyBottomSheetShown) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            val context = (LocalContext.current.applicationContext)
            val factory =
                (context as AccountComponentProvider).provideAccountComponent().viewModelFactory
            val currencyViewModel = viewModel<CurrencyViewModel>(
                viewModelStoreOwner = navBackStackEntry!!,
                factory = factory
            )
            val accountViewModel = viewModel<AccountViewModel>(
                viewModelStoreOwner = navBackStackEntry!!,
                factory = factory
            )

            CurrencyBottomSheet(
                modifier = Modifier,

                // this onClick function is called AFTER viewModel's implementation - in series (not in parallel)
                onClick = {
                    accountViewModel.tryLoadAndUpdateState()

                    appState.isCurrencyBottomSheetShown = false
                },

                onDismiss = {
                    accountViewModel.tryLoadAndUpdateState()

                    appState.isCurrencyBottomSheetShown = false
                },
                viewModel = currencyViewModel,
            )
        }
    }

    if (appState.isAccountSelectorShown) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            val context = (LocalContext.current.applicationContext)
            val factory =
                (context as AccountComponentProvider).provideAccountComponent().viewModelFactory
            val accountBottomSheetViewModel =
                viewModel<AccountBottomSheetViewModel>(factory = factory)

            // TODO: ref)
            val onAccountChange =
                if (currentRoute?.contains(ADD_INCOME_SCREEN_ROUTE_BASE) == true) {
                    val addIncomeViewModel = viewModel<AddIncomeViewModel>(
                        viewModelStoreOwner = navBackStackEntry!!,
                        factory = factory
                    )
                    addIncomeViewModel::onAccountChange
                } else if (currentRoute?.contains(ADD_EXPENSE_SCREEN_ROUTE_BASE) == true) {
                    val addExpenseViewModel = viewModel<AddExpenseViewModel>(
                        viewModelStoreOwner = navBackStackEntry!!,
                        factory = factory
                    )
                    addExpenseViewModel::onAccountChange
                } else if (currentRoute?.contains(EDIT_EXPENSE_SCREEN_ROUTE_BASE) == true) {
                    val factory =
                        (context as ExpensesComponentProvider).provideExpensesComponent().assistedFactory
                    val editExpenseViewModel = viewModel<EditExpenseViewModel>(
                        viewModelStoreOwner = navBackStackEntry!!,
                        factory = AssistedViewModelFactory(
                            factory
                        )
                    )
                    editExpenseViewModel::onAccountChange
                } else if (currentRoute?.contains(EDIT_INCOME_SCREEN_ROUTE_BASE) == true) {
                    val factory =
                        (context as IncomesComponentProvider).provideIncomesComponent().assistedFactory
                    val editIncomeViewModel = viewModel<EditIncomeViewModel>(
                        viewModelStoreOwner = navBackStackEntry!!,
                        factory = ru.ttb220.income.presentation.viewmodel.factory.AssistedViewModelFactory(
                            factory
                        )
                    )
                    editIncomeViewModel::onAccountChange
                } else {
                    { _: String, _: Int -> }
                }

            AccountBottomSheet(
                viewModel = accountBottomSheetViewModel,
                onAccountClick = {
                    onAccountChange(
                        it.accountName,
                        it.accountId
                    )
                },
                onDismiss = {
                    appState.isAccountSelectorShown = false
                }
            )
        }
    }

    if (appState.isCategorySelectorShown) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            val context = (LocalContext.current.applicationContext)
            val factory =
                (context as AccountComponentProvider).provideAccountComponent().viewModelFactory
            val categoryBottomSheetViewModel =
                viewModel<CategoryBottomSheetViewModel>(factory = factory)

            // TODO: ref)))
            val onCategoryChange =
                if (currentRoute?.contains(ADD_INCOME_SCREEN_ROUTE_BASE) == true) {
                    val addIncomeViewModel = viewModel<AddIncomeViewModel>(
                        viewModelStoreOwner = navBackStackEntry!!,
                        factory = factory
                    )
                    addIncomeViewModel::onCategoryChange
                } else if (currentRoute?.contains(ADD_EXPENSE_SCREEN_ROUTE_BASE) == true) {
                    val addExpenseViewModel = viewModel<AddExpenseViewModel>(
                        viewModelStoreOwner = navBackStackEntry!!,
                        factory = factory
                    )
                    addExpenseViewModel::onCategoryChange
                } else if (currentRoute?.contains(EDIT_EXPENSE_SCREEN_ROUTE_BASE) == true) {
                    val factory =
                        (context as ExpensesComponentProvider).provideExpensesComponent().assistedFactory
                    val editExpenseViewModel = viewModel<EditExpenseViewModel>(
                        viewModelStoreOwner = navBackStackEntry!!,
                        factory = AssistedViewModelFactory(factory)
                    )
                    editExpenseViewModel::onCategoryChange
                } else if (currentRoute?.contains(EDIT_INCOME_SCREEN_ROUTE_BASE) == true) {
                    val factory =
                        (context as IncomesComponentProvider).provideIncomesComponent().assistedFactory
                    val editIncomeViewModel = viewModel<EditIncomeViewModel>(
                        viewModelStoreOwner = navBackStackEntry!!,
                        factory = ru.ttb220.income.presentation.viewmodel.factory.AssistedViewModelFactory(
                            factory
                        )
                    )
                    editIncomeViewModel::onCategoryChange
                } else {
                    { _: String, _: Int -> }
                }

            if (currentRoute?.contains(TOP_LEVEL_INCOMES_ROUTE) == true) {
                categoryBottomSheetViewModel.type = CategoryBottomSheetType.INCOMES
            } else if (currentRoute?.contains(TOP_LEVEL_EXPENSES_ROUTE) == true) {
                categoryBottomSheetViewModel.type = CategoryBottomSheetType.EXPENSES
            } else {
                categoryBottomSheetViewModel.type = CategoryBottomSheetType.UNSPECIFIED
            }

            CategoryBottomSheet(
                viewModel = categoryBottomSheetViewModel,
                onCategoryClick = {
                    onCategoryChange(
                        it.categoryName,
                        it.categoryId
                    )
                },
                onDismiss = {
                    appState.isCategorySelectorShown = false
                }
            )
        }
    }
}