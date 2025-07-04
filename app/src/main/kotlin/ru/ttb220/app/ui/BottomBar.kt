package ru.ttb220.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.ttb220.app.navigation.TopLevelDestination
import ru.ttb220.presentation.model.BottomBarItemData
import ru.ttb220.presentation.ui.component.BottomBarItem

/**
 * Implementation of bottom bar as a component would not allow
 * to make it type-safe as TopLevelDestination is in app module
 */
@Composable
fun BottomBar(
    destinations: List<TopLevelDestination>,
    currentTopLevelDestination: TopLevelDestination?,
    modifier: Modifier = Modifier,
    onNavigateTo: (TopLevelDestination) -> Unit = {},
) {
    Surface(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .windowInsetsPadding(
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