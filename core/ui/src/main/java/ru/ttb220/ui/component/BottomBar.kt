package ru.ttb220.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.presentation_model.NavigationResource
import ru.ttb220.ui.R
import ru.ttb220.ui.theme.Green
import ru.ttb220.ui.theme.GreenHighlight

@Composable
fun BottomBar(
    destinations: List<NavigationResource>,
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
            destinations.forEachIndexed { index, it ->
                BottomBarItem(
                    navigationResource = it,
                    modifier = Modifier.weight(1f),
                    onNavigateTo = onNavigateTo
                )
                if (index != destinations.lastIndex)
                    Spacer(Modifier.width(8.dp))
            }
        }
    }
}

/**
 * Hugs content by default
 */
@Composable
private fun BottomBarItem(
    navigationResource: NavigationResource,
    modifier: Modifier = Modifier,
    onNavigateTo: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .padding(top = 12.dp, bottom = 16.dp)
            .clickable(
                interactionSource,
                null
            ) {
                onNavigateTo(navigationResource.route)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val backgroundModifier = Modifier.background(
            GreenHighlight,
            RoundedCornerShape(16.dp)
        )
        // icon-container
        Box(
            modifier = Modifier
                .size(
                    width = 64.dp,
                    height = 32.dp
                )
                .let {
                    if (navigationResource.isSelected)
                        it.then(backgroundModifier) else it
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(navigationResource.iconId),
                null,
                Modifier.size(24.dp),
                tint = if (navigationResource.isSelected) Green else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = stringResource(navigationResource.textId),
            modifier = Modifier,
            color = if (navigationResource.isSelected) MaterialTheme.colorScheme.onSurface else
                MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (navigationResource.isSelected) FontWeight.SemiBold else null,
            textAlign = TextAlign.Center,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
private fun BottomBarPreview() {
    BottomBar(
        destinations = listOf(
            NavigationResource(
                route = "",
                iconId = R.drawable.downtrend,
                textId = ru.ttb220.mock.R.string.expenses,
                isSelected = true,
            ),
            NavigationResource(
                route = "",
                iconId = R.drawable.uptrend,
                textId = ru.ttb220.mock.R.string.incomes,
                isSelected = false,
            ),
            NavigationResource(
                route = "",
                iconId = R.drawable.calculator,
                textId = ru.ttb220.mock.R.string.account,
                isSelected = false,
            ),
            NavigationResource(
                route = "",
                iconId = R.drawable.barchartside,
                textId = ru.ttb220.mock.R.string.articles,
                isSelected = false,
            ),
            NavigationResource(
                route = "",
                iconId = R.drawable.settings,
                textId = ru.ttb220.mock.R.string.settings,
                isSelected = false,
            ),
        )
    )
}