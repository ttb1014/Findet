package ru.ttb220.setting.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.model.ThemeState
import ru.ttb220.presentation.model.screen.SettingsScreenData
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    val isDarkThemeEnabled = settingsRepository.isDarkModeEnabled()

    val settingsScreenData = isDarkThemeEnabled.map {
        SettingsScreenData(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(SUBSCRIPTION_DELAY),
        initialValue = SettingsScreenData(false)
    )

    fun onThemeSelected(themeState: ThemeState) {
        viewModelScope.launch {
            settingsRepository.setThemeState(themeState)
        }
    }

    fun onHapticsSet(state: Boolean) {
        viewModelScope.launch {
            settingsRepository.setHapticsEnabled(state)
        }
    }

    companion object {
        private const val SUBSCRIPTION_DELAY = 5000L
    }
}