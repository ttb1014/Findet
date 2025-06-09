package ru.ttb220.demo.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ru.ttb220.demo.ui.navigation.FindetNavHost
import ru.ttb220.demo.ui.navigation.Destination
import ru.ttb220.model.NavigationResource
import ru.ttb220.ui.R
import ru.ttb220.ui.component.BottomBar
import ru.ttb220.ui.component.ButtonCircle
import ru.ttb220.ui.component.TopAppBar

@Composable
fun FindetApp(
    navHostController: NavHostController,
    currentRoute: String,
    modifier: Modifier = Modifier
) {
    // TODO: change color of status and navigation bars
    Scaffold(
        modifier = Modifier,
        topBar = {
            TopAppBar(
                text = "Расходы сегодня",
                leadingIcon = null,
                trailingIcon = R.drawable.history,
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.systemBars
                            .only(WindowInsetsSides.Top)
                    )
            )
        },
        bottomBar = {
            BottomBar(
                destinations = Destination.entries.map {
                    NavigationResource(
                        route = it.name,
                        iconId = it.iconId,
                        textId = it.textId,
                        isSelected = currentRoute == it.name
                    )
                },
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.systemBars
                            .only(WindowInsetsSides.Bottom)
                    )
            )
        },
        floatingActionButton = { ButtonCircle() },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = MaterialTheme.colorScheme.surface,
    ) { padding ->
        FindetNavHost(
            navHostController = navHostController,
            modifier = Modifier.padding(padding),
        )
    }
}