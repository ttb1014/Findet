package ru.ttb220.demo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.ttb220.demo.navigation.Destination
import ru.ttb220.demo.navigation.FindetNavHost
import ru.ttb220.demo.navigation.FloatingActionButtonDestinations
import ru.ttb220.model.NavigationResource
import ru.ttb220.ui.component.BottomBar
import ru.ttb220.ui.component.FloatingActionButtonAdd
import ru.ttb220.ui.component.TopAppBar

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
                    NavigationResource(
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
                FloatingActionButtonAdd()
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.surface,
    ) { padding ->
        FindetNavHost(
            navHostController = appState.navHostController,
            modifier = Modifier.padding(padding),
            startRoute
        )
    }
}