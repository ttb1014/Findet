package ru.ttb220.setting.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ttb220.presentation.model.R
import ru.ttb220.setting.presentation.viewmodel.SettingsViewModel
import kotlin.math.roundToInt

@Composable
fun SyncFrequencyScreen(
    viewModel: SettingsViewModel,
    modifier: Modifier = Modifier
) {
    SyncFrequencyScreen(
        modifier,
        viewModel::onSyncFreqSelected
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyncFrequencyScreen(
    modifier: Modifier = Modifier,
    onSyncFreqSelected: (Int) -> Unit
) {
    var sliderValue by remember { mutableIntStateOf(1) }

    val sliderSteps = 23
    val sliderRange = 1f..24f

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.sync_frequency) +
                    ": ${sliderValue}" +
                    stringResource(R.string.hour_symbol) +
                    ".",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Slider(
            value = sliderValue.toFloat(),
            onValueChange = {
                sliderValue =
                    it.roundToInt()
            },
            valueRange = sliderRange,
            steps = sliderSteps,
            onValueChangeFinished = {
                onSyncFreqSelected(sliderValue)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}