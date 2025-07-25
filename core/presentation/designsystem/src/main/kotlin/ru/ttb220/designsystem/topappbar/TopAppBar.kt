package ru.ttb220.designsystem.topappbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.presentation.model.R

/**
 * @param text Должен вмещаться в контейнер
 */
@Composable
fun TopAppBar(
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
    onLeadingIconClick: () -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
    isConnected: Boolean = true,
    lastSyncFormattedTime: String,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val surfaceColor = if (!isConnected) MaterialTheme.colorScheme.error else color

    Surface(
        modifier = modifier,
        color = surfaceColor,
    ) {
        Box(
            Modifier
                .height(
                    WindowInsets.systemBars
                        .only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
                        .getTop(LocalDensity.current).dp
                )
                .fillMaxWidth(),
            contentAlignment = Alignment.TopCenter
        ) {
            when (isConnected) {
                true -> Text(
                    text = stringResource(R.string.last_synced) + lastSyncFormattedTime,
                    color = MaterialTheme.colorScheme.onSurface,
                    softWrap = false,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium
                )

                false -> Text(
                    text = stringResource(R.string.offline) + lastSyncFormattedTime,
                    color = MaterialTheme.colorScheme.onPrimary,
                    softWrap = false,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        Row(
            modifier = modifier
                .windowInsetsPadding(
                    WindowInsets.systemBars
                        .only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)
                )
                .height(64.dp)
                .fillMaxWidth()
                .background(color)
                .padding(horizontal = 4.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let {
                val interactionSource = remember { MutableInteractionSource() }

                TopAppBarIcon(
                    leadingIcon,
                    MaterialTheme.colorScheme.onSurface,
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onLeadingIconClick
                    )
                )
            } ?: Spacer(Modifier.size(48.dp))

            Text(
                text = text,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                softWrap = false,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge
            )

            trailingIcon?.let {
                val interactionSource = remember { MutableInteractionSource() }

                TopAppBarIcon(
                    trailingIcon,
                    MaterialTheme.colorScheme.onSurfaceVariant,
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = null,
                        onClick = onTrailingIconClick
                    )
                )
            } ?: Spacer(Modifier.size(48.dp))
        }
    }
}

@Composable
private fun TopAppBarIcon(
    @DrawableRes iconId: Int,
    tint: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(48.dp)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = tint
        )
    }
}

@Preview
@Composable
private fun TopAppBarPreview() {
    TopAppBar(
        text = "Мои расходы",
        leadingIcon = R.drawable.cross,
        trailingIcon = R.drawable.check,
        modifier = Modifier,
        isConnected = true,
        lastSyncFormattedTime = "2024-12-01",
    )
}