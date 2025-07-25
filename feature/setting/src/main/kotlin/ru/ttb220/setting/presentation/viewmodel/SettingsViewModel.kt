package ru.ttb220.setting.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.model.SupportedLanguage
import ru.ttb220.model.ThemeState
import ru.ttb220.presentation.model.screen.SettingsScreenData
import ru.ttb220.setting.BuildConfig
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    val appVersion = BuildConfig.APP_VERSION
    val lastUpdateTime = BuildConfig.APP_LAST_UPDATE
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

    fun onSyncFreqSelected(freq: Int) {
        viewModelScope.launch {
            settingsRepository.setSyncFrequency(freq.hourToMillis())
        }
    }

    fun onLanguageSelected(language: SupportedLanguage) {
        viewModelScope.launch {
            settingsRepository.setActiveLanguage(language)
        }
    }

    fun onDarkModeClick(enabled: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkModeEnabled(enabled)
        }
    }

    fun Int.hourToMillis() =
        this * 60 * 60 * 1000L

    companion object {
        private const val SUBSCRIPTION_DELAY = 5000L
    }
}