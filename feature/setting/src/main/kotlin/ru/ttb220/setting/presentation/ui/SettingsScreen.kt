package ru.ttb220.setting.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.designsystem.ColumnListItem
import ru.ttb220.designsystem.Switch
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.SettingsScreenData
import ru.ttb220.setting.presentation.mock.mockSettingsScreenContent
import ru.ttb220.setting.presentation.model.SettingsDestination

@Composable
fun SettingsScreen(
    settingsScreenData: SettingsScreenData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        LightDarkAutoTheme(
            settingsScreenData.isDarkThemeEnabled
        )
        SettingsDestination.entries.forEach {
            SettingsItem(it)
        }
    }
}

@Composable
fun LightDarkAutoTheme(
    isDarkThemeEnabled: Boolean,
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

            Switch(isDarkThemeEnabled)
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
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
    SettingsScreen(mockSettingsScreenContent)
}