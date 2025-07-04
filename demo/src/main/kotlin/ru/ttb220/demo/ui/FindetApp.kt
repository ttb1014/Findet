package ru.ttb220.demo.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.demo.navigation.Destination
import ru.ttb220.demo.navigation.FindetNavHost
import ru.ttb220.demo.navigation.FloatingActionButtonDestinations
import ru.ttb220.presentation.model.BottomBarItemData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.ui.component.AddFab
import ru.ttb220.presentation.ui.component.BottomBarItem
import ru.ttb220.presentation.ui.component.topappbar.TopAppBar

@Composable
fun FindetApp(
    appState: AppState,
    startRoute: String,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                text = appState.currentDestination?.let {
                    stringResource(it.topAppBarTextId)
                } ?: "",
                leadingIcon = appState.currentDestination?.topAppBarLeadingIconInt,
                trailingIcon = appState.currentDestination?.topAppBarTrailingIconInt,
            )
        },
        bottomBar = {
            BottomBar(
                destinations = Destination.entries.map {
                    BottomBarItemData(
                        route = it.name,
                        iconId = it.iconId,
                        textId = it.textId,
                        isSelected = appState.currentRoute == it.name
                    )
                },
                onNavigateTo = appState::navigateTo
            )
        },
        floatingActionButton = {
            if (FloatingActionButtonDestinations.contains(appState.currentDestination))
                AddFab()
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.surface,
    ) { padding ->
        FindetNavHost(
            navHostController = appState.navHostController,
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding),
            startRoute
        )
    }
}

@Composable
fun BottomBar(
    destinations: List<BottomBarItemData>,
    modifier: Modifier = Modifier,
    onNavigateTo: (String) -> Unit = {},
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
            destinations.forEachIndexed { index, navigationData ->
                BottomBarItem(
                    bottomBarItemData = navigationData,
                    modifier = Modifier.weight(1f),
                    onNavigateTo = onNavigateTo
                )
                if (index != destinations.lastIndex)
                    Spacer(Modifier.width(8.dp))
            }
        }
    }
}


@Preview
@Composable
private fun BottomBarPreview() {
    BottomBar(
        destinations = listOf(
            BottomBarItemData(
                route = "",
                iconId = R.drawable.downtrend,
                textId = ru.ttb220.mock.R.string.expenses,
                isSelected = true,
            ),
            BottomBarItemData(
                route = "",
                iconId = R.drawable.uptrend,
                textId = ru.ttb220.mock.R.string.incomes,
                isSelected = false,
            ),
            BottomBarItemData(
                route = "",
                iconId = R.drawable.calculator,
                textId = ru.ttb220.mock.R.string.account,
                isSelected = false,
            ),
            BottomBarItemData(
                route = "",
                iconId = R.drawable.barchartside,
                textId = ru.ttb220.mock.R.string.articles,
                isSelected = false,
            ),
            BottomBarItemData(
                route = "",
                iconId = R.drawable.settings,
                textId = ru.ttb220.mock.R.string.settings,
                isSelected = false,
            ),
        )
    )
}