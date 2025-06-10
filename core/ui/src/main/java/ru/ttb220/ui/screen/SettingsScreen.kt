package ru.ttb220.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockSettingsScreenResource
import ru.ttb220.model.SettingsScreenResource
import ru.ttb220.ui.R
import ru.ttb220.ui.component.ColumnListItem

private enum class SettingsDestination(
    @StringRes val textId: Int
) {
    MAIN_COLOR(
        R.string.main_color
    ),
    SOUNDS(
        R.string.sounds
    ),
    HAPTICS(
        R.string.haptics
    ),
    PASSWORD(
        R.string.password
    ),
    SYNCHRONIZATION(
        R.string.sync
    ),
    LANGUAGE(
        R.string.lang
    ),
    INFO(
        R.string.info
    )
}

@Composable
fun SettingsScreen(
    settingsScreenResource: SettingsScreenResource,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        LightDarkAutoTheme()
        SettingsDestination.entries.forEach {
            SettingsItem(it)
        }
    }
}

@Composable
fun LightDarkAutoTheme(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Светлая темная авто",
                modifier = Modifier
                    .weight(1f),
                color = MaterialTheme.colorScheme.onSurface,
                softWrap = false,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )

            Switch()
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Composable
private fun Switch(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(
                width = 52.dp,
                height = 32.dp
            )
            .border(2.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(100.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center,
    ) {
        val color = MaterialTheme.colorScheme.outline

        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    onDrawBehind {
                        drawCircle(
                            color = color,
                            radius = 8.dp.toPx(),
                            center = Offset(
                                8.dp.toPx(),
                                size.height / 2
                            ),
                        )
                    }
                }
        )
    }
}

@Composable
private fun SettingsItem(
    settingsDestination: SettingsDestination,
    modifier: Modifier = Modifier
) {
    ColumnListItem(
        title = stringResource(settingsDestination.textId),
        modifier = modifier.height(56.dp),
        trailingIcon = R.drawable.more_right_solid,
        trailingIconTint = MaterialTheme.colorScheme.onSurfaceVariant,
        shouldShowTrailingDivider = true
    )
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(mockSettingsScreenResource)
}