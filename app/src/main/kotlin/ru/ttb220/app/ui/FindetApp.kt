package ru.ttb220.app.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.ttb220.account.add.ADD_ACCOUNT_SCREEN_ROUTE
import ru.ttb220.app.navigation.FabRoutes
import ru.ttb220.app.navigation.FindetNavHost
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.presentation.model.BottomBarItemData
import ru.ttb220.presentation.ui.component.AddFab
import ru.ttb220.presentation.ui.component.BottomBarItem
import ru.ttb220.presentation.ui.component.TopAppBar

@Composable
fun FindetApp(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    val currentRoute = appState.currentRoute
    val currentTopLevelDestination = appState.currentTopLevelDestination
    val navBackStackEntry by appState.navHostController.currentBackStackEntryAsState()

    val onTrailingIconClick: () -> Unit = appState.onTabTrailingIconClick(navBackStackEntry)
    val onLeadingIconClick: () -> Unit = appState.onTabLeadingIconClick()

    val topAppBarData = appState.topAppBarData()

    Scaffold(
        modifier = modifier,
        topBar = {
            if(topAppBarData == null) {
                Log.d(":SADAS", "FindetApp: :SDAS")
            }

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
                currentTopLevelDestination = appState.currentTopLevelDestination,
                onNavigateTo = appState::navigateTo
            )
        },
        floatingActionButton = {
            if (FabRoutes.any { currentRoute?.contains(it) == true })
                AddFab {
                    when (currentTopLevelDestination) {
                        TopLevelDestination.ACCOUNT ->
                            appState.navigateTo(ADD_ACCOUNT_SCREEN_ROUTE)

                        else -> {}
                    }
                }
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

@Composable
private fun BottomBar(
    destinations: List<TopLevelDestination>,
    currentTopLevelDestination: TopLevelDestination?,
    modifier: Modifier = Modifier,
    onNavigateTo: (TopLevelDestination) -> Unit = {},
) {
    Surface(
        modifier = modifier.windowInsetsPadding(
            WindowInsets.systemBars
                .only(WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            destinations.forEachIndexed { index, topLevelDestination ->
                val bottomBarItemData = BottomBarItemData(
                    route = topLevelDestination.route,
                    iconId = topLevelDestination.iconId,
                    textId = topLevelDestination.textId,
                    isSelected = currentTopLevelDestination == topLevelDestination
                )

                BottomBarItem(
                    bottomBarItemData = bottomBarItemData,
                    modifier = Modifier.weight(1f),
                    onNavigateTo = {
                        onNavigateTo(topLevelDestination)
                    }
                )
                if (index != destinations.lastIndex)
                    Spacer(Modifier.width(8.dp))
            }
        }
    }
}