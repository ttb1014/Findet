package ru.ttb220.setting.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ttb220.designsystem.component.ThreeComponentListItem
import ru.ttb220.presentation.model.R
import ru.ttb220.setting.presentation.viewmodel.SettingsViewModel

@Composable
fun InfoScreen(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier,
) {
    InfoScreen(
        version = viewModel.appVersion,
        lastUpdateTime = viewModel.lastUpdateTime
    )
}

@Composable
fun InfoScreen(
    version: String,
    lastUpdateTime: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        ThreeComponentListItem(
            modifier = Modifier.height(56.dp),
            shouldShowTrailingDivider = true,
            leadingContent = {
                Text(
                    text = stringResource(R.string.version) + ": ",
                    maxLines = 1,
                    minLines = 1,
                    softWrap = false,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingContent = {
                Text(
                    text = version,
                    maxLines = 1,
                    minLines = 1,
                    softWrap = false,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            centerContent = {
                Spacer(modifier = it)
            }
        )
        ThreeComponentListItem(
            modifier = Modifier.height(56.dp),
            shouldShowTrailingDivider = true,
            leadingContent = {
                Text(
                    text = stringResource(R.string.last_update) + ": ",
                    maxLines = 1,
                    minLines = 1,
                    softWrap = false,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            trailingContent = {
                Text(
                    text = lastUpdateTime,
                    maxLines = 1,
                    minLines = 1,
                    softWrap = false,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            centerContent = {
                Spacer(modifier = it)
            }
        )
    }
}