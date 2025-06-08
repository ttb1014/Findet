package ru.ttb220.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.ui.model.DestinationResource
import ru.ttb220.ui.model.TopLevelDestination

@Composable
fun BottomBar(
    destinations: List<DestinationResource>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Start,
    ) {
        destinations.forEachIndexed { index, it ->
            BottomBarItem(
                destination = it,
                modifier = Modifier.weight(1f)
            )
            if (index != destinations.lastIndex)
                Spacer(Modifier.width(8.dp))
        }
    }
}

val DEFAULT_ACTIVE_DESTINATION_HIGHLIGHT = Color(0xFFD4FAE6)
val DEFAULT_ACTIVE_DESTINATION_ICON_TINT = Color(0xFF2AE881)

/**
 * Hugs content by default
 */
@Composable
private fun BottomBarItem(
    destination: DestinationResource,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val backgroundModifier = Modifier.background(
            DEFAULT_ACTIVE_DESTINATION_HIGHLIGHT,
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
                    if (destination.isActive)
                        it.then(backgroundModifier) else it
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(destination.destination.iconId),
                null,
                Modifier,
                tint = if (destination.isActive) DEFAULT_ACTIVE_DESTINATION_ICON_TINT else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = destination.destination.destinationName,
            modifier = Modifier,
            color = if (destination.isActive) MaterialTheme.colorScheme.onSurfaceVariant else
                MaterialTheme.colorScheme.onSurface,
            fontWeight = if (destination.isActive) FontWeight.SemiBold else null,
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
        destinations = TopLevelDestination.entries.mapIndexed { index, element ->
            DestinationResource(
                element,
                if (index == 0) true else false
            )
        },
    )
}