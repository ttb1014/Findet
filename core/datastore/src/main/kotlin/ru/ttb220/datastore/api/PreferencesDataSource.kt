package ru.ttb220.datastore.api

import kotlinx.coroutines.flow.Flow
import ru.ttb220.model.SupportedLanguage
import ru.ttb220.model.ThemeState

interface PreferencesDataSource {

    val themeStateFlow: Flow<ThemeState>
    suspend fun setThemeState(themeState: ThemeState)
    suspend fun getThemeState(): ThemeState

    val syncFrequencyFlow: Flow<Long>
    suspend fun setSyncFrequency(freq: Long)
    suspend fun getSyncFrequency(): Long

    val isHapticsEnabledFlow: Flow<Boolean>
    suspend fun setHapticsEnabled(enabled: Boolean)
    suspend fun getHapticsEnabled(): Boolean

    val lastSyncTimeFlow: Flow<Long>
    suspend fun updateLastSyncTime(lastSync: Long)
    suspend fun getLastSyncTime(): Long

    val selectedLanguageFlow: Flow<SupportedLanguage>
    suspend fun setLanguage(language: SupportedLanguage)
    suspend fun getLanguage(): SupportedLanguage

    val isDarkModeEnabledFlow: Flow<Boolean>
    suspend fun setDarkMode(enabled: Boolean)
    suspend fun getDarkMode(): Boolean
}