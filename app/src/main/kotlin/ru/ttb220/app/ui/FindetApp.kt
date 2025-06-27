package ru.ttb220.app.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.ttb220.app.navigation.FabRoutes
import ru.ttb220.app.navigation.FindetNavHost
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.presentation.ui.component.AddFab
import ru.ttb220.presentation.ui.component.TopAppBar

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
        topBar = {
            topAppBarData?.let {
                TopAppBar(
                    text = stringResource(topAppBarData.textId),
                    leadingIcon = topAppBarData.leadingIconId,
                    trailingIcon = topAppBarData.trailingIconId,
                    onLeadingIconClick = onLeadingIconClick,
                    onTrailingIconClick = onTrailingIconClick
                )
            }
        },
        bottomBar = {
            BottomBar(
                destinations = TopLevelDestination.entries,
                currentTopLevelDestination = currentTopLevelDestination,
                onNavigateTo = {
                    if (currentTopLevelDestination != it)
                        appState.navigateTo(it)
                }
            )
        },
        floatingActionButton = {
            if (FabRoutes.any { currentRoute?.contains(it) == true })
                AddFab(
                    onClick = fabOnClick
                )
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.surface,
    ) { padding ->
        FindetNavHost(
            navHostController = appState.navHostController,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        )
    }
}