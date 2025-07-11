package ru.ttb220.app.ui

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.ttb220.account.di.AccountComponentProvider
import ru.ttb220.account.presentation.ui.AccountEditableTopAppBar
import ru.ttb220.account.presentation.viewmodel.AccountViewModel
import ru.ttb220.app.navigation.FabRoutes
import ru.ttb220.app.navigation.FindetNavHost
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.bottomsheet.presentation.model.CategoryBottomSheetType
import ru.ttb220.bottomsheet.presentation.ui.AccountBottomSheet
import ru.ttb220.bottomsheet.presentation.ui.CategoryBottomSheet
import ru.ttb220.bottomsheet.presentation.ui.CurrencyBottomSheet
import ru.ttb220.bottomsheet.presentation.viewmodel.AccountBottomSheetViewModel
import ru.ttb220.bottomsheet.presentation.viewmodel.CategoryBottomSheetViewModel
import ru.ttb220.bottomsheet.presentation.viewmodel.CurrencyViewModel
import ru.ttb220.expenses.presentation.navigation.TOP_LEVEL_EXPENSES_ROUTE
import ru.ttb220.incomes.presentation.navigation.TOP_LEVEL_INCOMES_ROUTE
import ru.ttb220.incomes.presentation.viewmodel.AddIncomeViewModel
import ru.ttb220.presentation.ui.component.AddFab
import ru.ttb220.presentation.ui.component.topappbar.TopAppBar
import ru.ttb220.presentation.ui.util.scrim

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
                                appState.isBottomSheetShown
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
                        }
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
                            if (appState.isBottomSheetShown
                                || appState.isAccountSelectorShown
                                || appState.isCategorySelectorShown
                            ) {
                                val scrim = MaterialTheme.colorScheme.scrim

                                it.scrim(scrim)
                            } else
                                it
                        }
                )
            }
        },
        bottomBar = bottomBar@{
            if (appState.isBottomSheetShown.not()) {
                BottomBar(
                    destinations = TopLevelDestination.entries,
                    currentTopLevelDestination = currentTopLevelDestination,
                    onNavigateTo = {
                        if (currentTopLevelDestination != it)
                            appState.navigateTo(it)
                    }
                )
                return@bottomBar
            }
        },
        floatingActionButton = {
            if (FabRoutes.any { currentRoute?.contains(it) == true } && appState.isBottomSheetShown.not())
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
                        appState.isBottomSheetShown
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

    // BottomSheetFeature. Callback changing active currency is called from viewModel.
    // viewModel is retrieved from viewModelStoreOwner (navBackStackEntry)
    if (appState.isBottomSheetShown) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            val context = (LocalContext.current.applicationContext)
            val factory =
                (context as AccountComponentProvider).provideAccountComponent().viewModelFactory
            val currencyViewModel = viewModel<CurrencyViewModel>(factory = factory)
            val accountViewModel = viewModel<AccountViewModel>(factory = factory)

            CurrencyBottomSheet(
                modifier = Modifier,

                // this onClick function is called AFTER viewModel's implementation - in series (not in parallel)
                onClick = {
                    accountViewModel.tryLoadAndUpdateState()

                    appState.isBottomSheetShown = false
                },

                onDismiss = {
                    accountViewModel.tryLoadAndUpdateState()

                    appState.isBottomSheetShown = false
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
            val addIncomeViewModel = viewModel<AddIncomeViewModel>(factory = factory)

            AccountBottomSheet(
                viewModel = accountBottomSheetViewModel,
                onAccountClick = {
                    addIncomeViewModel.onAccountChange(
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
            val addIncomeViewModel = viewModel<AddIncomeViewModel>(factory = factory)

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
                    addIncomeViewModel.onCategoryChange(
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