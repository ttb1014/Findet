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
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.ttb220.account.presentation.ui.AccountEditableTopAppBar
import ru.ttb220.account.presentation.viewmodel.AccountViewModel
import ru.ttb220.app.navigation.FabRoutes
import ru.ttb220.app.navigation.FindetNavHost
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.currencyselector.presentation.ui.CurrencyBottomSheet
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

    // callback resolution is delegated to appState. NavBackStackEntry is required to get
    // a callback from viewModel instance
    val onTrailingIconClick: () -> Unit = appState.onTabTrailingIconClick(navBackStackEntry)
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
                    AccountEditableTopAppBar(
                        modifier = Modifier.let {
                            if (appState.isBottomSheetShown) {
                                val scrim = MaterialTheme.colorScheme.scrim

                                it.scrim(scrim)
                            } else
                                it
                        },
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
                            if (appState.isBottomSheetShown) {
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
                    if (appState.isBottomSheetShown)
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
            val accountViewModel = navBackStackEntry?.let { entry ->
                hiltViewModel<AccountViewModel>(entry)
            }

            CurrencyBottomSheet(
                modifier = Modifier,

                // this onClick function is called AFTER viewModel's implementation - in series (not in parallel)
                onClick = {
                    accountViewModel?.tryLoadAndUpdateState()
                },

                onDismiss = {
                    accountViewModel?.tryLoadAndUpdateState()

                    appState.isBottomSheetShown = false
                },
            )
        }
    }
}