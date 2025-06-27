package ru.ttb220.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.ttb220.presentation.model.BottomBarItemData
import ru.ttb220.presentation.ui.theme.Green
import ru.ttb220.presentation.ui.theme.GreenHighlight

/**
 * Hugs content by default
 */
@Composable
fun BottomBarItem(
    bottomBarItemData: BottomBarItemData,
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
                onNavigateTo(bottomBarItemData.route)
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
                    if (bottomBarItemData.isSelected)
                        it.then(backgroundModifier) else it
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(bottomBarItemData.iconId),
                null,
                Modifier.size(24.dp),
                tint = if (bottomBarItemData.isSelected) Green else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = stringResource(bottomBarItemData.textId),
            modifier = Modifier,
            color = if (bottomBarItemData.isSelected) MaterialTheme.colorScheme.onSurface else
                MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (bottomBarItemData.isSelected) FontWeight.SemiBold else null,
            textAlign = TextAlign.Center,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.labelMedium
        )
    }
}