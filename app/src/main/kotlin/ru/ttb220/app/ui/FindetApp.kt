package ru.ttb220.app.ui

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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ttb220.app.navigation.FindetNavHost
import ru.ttb220.app.navigation.FloatingActionButtonDestinations
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.presentation.model.NavigationData
import ru.ttb220.presentation.ui.component.BottomBarItem
import ru.ttb220.presentation.ui.component.FloatingActionButtonAdd
import ru.ttb220.presentation.ui.component.TopAppBar

@Composable
fun FindetApp(
    appState: AppState,
    modifier: Modifier = Modifier
) {
    val currentTopLevelDestination = appState.currentTopLevelDestination

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                text = currentTopLevelDestination?.let {
                    stringResource(it.topAppBarTextId)
                } ?: "",
                leadingIcon = currentTopLevelDestination?.topAppBarLeadingIconInt,
                trailingIcon = currentTopLevelDestination?.topAppBarTrailingIconInt,
                onTrailingIconClick = {
                    when (currentTopLevelDestination) {
                        TopLevelDestination.EXPENSES -> appState.navigateToHistory(false)
                        TopLevelDestination.INCOMES -> appState.navigateToHistory(true)
                        else -> TODO()
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(
                destinations = TopLevelDestination.entries.filter {
                    it.name != TopLevelDestination.EXPENSES_HISTORY.name &&
                            it.name != TopLevelDestination.INCOMES_HISTORY.name
                },
                currentTopLevelDestination = appState.currentTopLevelDestination,
                onNavigateTo = appState::navigateTo
            )
        },
        floatingActionButton = {
            if (FloatingActionButtonDestinations.contains(appState.currentTopLevelDestination))
                FloatingActionButtonAdd()
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.surface,
    ) { padding ->
        FindetNavHost(
            navHostController = appState.navHostController,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding),
        )
    }
}

@Composable
private fun BottomBar(
    destinations: List<TopLevelDestination>,
    currentTopLevelDestination: TopLevelDestination?,
    onNavigateTo: (TopLevelDestination) -> Unit = {},
    modifier: Modifier = Modifier,
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
                var isSelected = currentTopLevelDestination == topLevelDestination

                if (currentTopLevelDestination == TopLevelDestination.INCOMES_HISTORY &&
                    topLevelDestination == TopLevelDestination.INCOMES
                ) {
                    isSelected = true
                }

                if (currentTopLevelDestination == TopLevelDestination.EXPENSES_HISTORY &&
                    topLevelDestination == TopLevelDestination.EXPENSES
                ) {
                    isSelected = true
                }

                val navigationData = NavigationData(
                    route = topLevelDestination.route,
                    iconId = topLevelDestination.iconId,
                    textId = topLevelDestination.textId,
                    isSelected = isSelected
                )

                BottomBarItem(
                    navigationData = navigationData,
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