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
import ru.ttb220.demo.FindetNavHost
import ru.ttb220.ui.R
import ru.ttb220.ui.component.BottomBar
import ru.ttb220.ui.component.ButtonCircle
import ru.ttb220.ui.component.TopAppBar
import ru.ttb220.ui.model.DestinationResource
import ru.ttb220.ui.model.TopLevelDestination

@Composable
fun FindetApp(
    navHostController: NavHostController,
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
                destinations = TopLevelDestination.entries.map {
                    DestinationResource(
                        destination = it,
                        isActive = false,
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