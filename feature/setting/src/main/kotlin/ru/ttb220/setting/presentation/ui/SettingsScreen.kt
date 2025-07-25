package ru.ttb220.setting.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.designsystem.component.ColumnListItem
import ru.ttb220.designsystem.component.Switch
import ru.ttb220.model.SupportedLanguages
import ru.ttb220.model.ThemeState
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.SettingsScreenData
import ru.ttb220.setting.presentation.mock.mockSettingsScreenContent
import ru.ttb220.setting.presentation.model.SettingsDestination
import ru.ttb220.setting.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier,
    navigateToSetupPin: () -> Unit = {},
    navigateToSyncFrequency: () -> Unit = {},
) {
    val settingsScreenData by settingsViewModel.settingsScreenData.collectAsStateWithLifecycle()

    SettingsScreen(
        settingsScreenData,
        modifier,
        onThemeSelected = settingsViewModel::onThemeSelected,
        onPinCodeClick = navigateToSetupPin,
        onSyncClick = navigateToSyncFrequency
    )
}

@Composable
fun SettingsScreen(
    settingsScreenData: SettingsScreenData,
    modifier: Modifier = Modifier,
    onThemeSelected: (ThemeState) -> Unit = {},
    onHapticsSet: (Boolean) -> Unit = {},
    onPinCodeClick: () -> Unit = {},
    onSyncClick: () -> Unit = {},
    onLanguageSelected: (SupportedLanguages) -> Unit = {},
    onInfoClicked: () -> Unit = {},
) {
    var expandedColors by remember { mutableStateOf(false) }
    var expandedHaptics by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
    ) {
        LightDarkTheme(
            settingsScreenData.isDarkThemeEnabled
        )
        // color item wrapper
        Box() {
            MainColorItem {
                expandedColors = true
            }
            DropdownMenu(
                expanded = expandedColors,
                onDismissRequest = { expandedColors = false },
            ) {
                ThemeState.entries.forEach { theme ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                theme.name,
                                softWrap = false,
                                maxLines = 1,
                                minLines = 1,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = {
                            onThemeSelected(theme)
                            expandedColors = false
                        }
                    )
                }
            }
        }
        SoundsItem()
        // haptics wrapper
        Box() {
            HapticsItem {
                expandedHaptics = true
            }
            DropdownMenu(
                expanded = expandedHaptics,
                onDismissRequest = { expandedHaptics = false },
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.haptics_on),
                            softWrap = false,
                            maxLines = 1,
                            minLines = 1,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onHapticsSet(true)
                        expandedHaptics = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.haptics_off),
                            softWrap = false,
                            maxLines = 1,
                            minLines = 1,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onHapticsSet(false)
                        expandedHaptics = false
                    }
                )
            }
        }
        PinItem(
            modifier = Modifier,
            onClick = onPinCodeClick
        )
        SyncItem(
            modifier = Modifier,
            onSyncClick
        )
        LangItem(
            onLanguageSelected = onLanguageSelected
        )
        InfoItem(
            onClick = onInfoClicked
        )
    }
}

@Composable
fun InfoItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ColumnListItem(
        title = stringResource(R.string.information),
        modifier = modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        trailingIcon = R.drawable.more_right_solid,
        trailingIconTint = MaterialTheme.colorScheme.onSurfaceVariant,
        shouldShowTrailingDivider = true,
    )
}

@Composable
fun LangItem(
    modifier: Modifier = Modifier,
    onLanguageSelected: (SupportedLanguages) -> Unit,
) {
    var expandedLanguages by remember { mutableStateOf(false) }

    Box() {
        ColumnListItem(
            title = stringResource(R.string.language),
            modifier = modifier
                .height(56.dp)
                .clickable {
                    expandedLanguages = true
                },
            trailingIcon = R.drawable.more_right_solid,
            trailingIconTint = MaterialTheme.colorScheme.onSurfaceVariant,
            shouldShowTrailingDivider = true,
        )

        DropdownMenu(
            expanded = expandedLanguages,
            onDismissRequest = { expandedLanguages = false },
        ) {
            SupportedLanguages.entries.forEach { theme ->
                DropdownMenuItem(
                    text = {
                        Text(
                            theme.name,
                            softWrap = false,
                            maxLines = 1,
                            minLines = 1,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onLanguageSelected(theme)
                        expandedLanguages = false
                    }
                )
            }
        }
    }
}

@Composable
fun SyncItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ColumnListItem(
        title = stringResource(R.string.synchronization),
        modifier = modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        trailingIcon = R.drawable.more_right_solid,
        trailingIconTint = MaterialTheme.colorScheme.onSurfaceVariant,
        shouldShowTrailingDivider = true,
    )
}

@Composable
fun PinItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ColumnListItem(
        title = stringResource(R.string.password),
        modifier = modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        trailingIcon = R.drawable.more_right_solid,
        trailingIconTint = MaterialTheme.colorScheme.onSurfaceVariant,
        shouldShowTrailingDivider = true,
    )
}

@Composable
fun HapticsItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    ColumnListItem(
        title = stringResource(R.string.haptics),
        modifier = modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        trailingIcon = R.drawable.more_right_solid,
        trailingIconTint = MaterialTheme.colorScheme.onSurfaceVariant,
        shouldShowTrailingDivider = true,
    )
}

@Composable
fun SoundsItem(
    modifier: Modifier = Modifier
) {
    ColumnListItem(
        title = stringResource(R.string.sounds),
        modifier = modifier
            .height(56.dp),
        trailingIcon = R.drawable.more_right_solid,
        trailingIconTint = MaterialTheme.colorScheme.onSurfaceVariant,
        shouldShowTrailingDivider = true,
    )
}

@Composable
fun LightDarkTheme(
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
                text = stringResource(R.string.light_dark),
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

@Composable
fun MainColorItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    ColumnListItem(
        title = stringResource(R.string.main_color),
        modifier = modifier
            .height(56.dp)
            .clickable(onClick = onClick),
        trailingIcon = R.drawable.more_right_solid,
        trailingIconTint = MaterialTheme.colorScheme.onSurfaceVariant,
        shouldShowTrailingDivider = true,
    )
}

@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(mockSettingsScreenContent)
}