package ru.ttb220.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.ttb220.datastore.api.PreferencesDataSource
import ru.ttb220.datastore.impl.DataStoreKeys.DARK_MODE_KEY
import ru.ttb220.datastore.impl.DataStoreKeys.IS_HAPTICS_ENABLED
import ru.ttb220.datastore.impl.DataStoreKeys.LANGUAGE_KEY
import ru.ttb220.datastore.impl.DataStoreKeys.LAST_SYNC_TIME
import ru.ttb220.datastore.impl.DataStoreKeys.SYNC_FREQUENCY
import ru.ttb220.datastore.impl.DataStoreKeys.THEME_STATE
import ru.ttb220.model.SupportedLanguage
import ru.ttb220.model.ThemeState
import javax.inject.Inject

class PreferencesDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : PreferencesDataSource {

    // ------------------ Theme State ------------------
    override val themeStateFlow: Flow<ThemeState> = dataStore.data
        .map { preferences ->
            val stored = preferences[THEME_STATE] ?: ""
            runCatching { ThemeState.valueOf(stored) }
                .getOrDefault(ThemeState.ORIGINAL)
        }

    override suspend fun setThemeState(themeState: ThemeState) {
        dataStore.edit { preferences ->
            preferences[THEME_STATE] = themeState.name
        }
    }

    override suspend fun getThemeState(): ThemeState {
        val stored = dataStore.data.map { it[THEME_STATE] ?: "" }.first()
        return runCatching { ThemeState.valueOf(stored) }
            .getOrDefault(ThemeState.ORIGINAL)
    }

    // ------------------ Sync Frequency ------------------
    override val syncFrequencyFlow: Flow<Long> = dataStore.data
        .map { prefs ->
            val stored = prefs[SYNC_FREQUENCY] ?: DEFAULT_SYNC_FREQUENCY
            runCatching { stored }
                .getOrDefault(DEFAULT_SYNC_FREQUENCY)
        }

    override suspend fun setSyncFrequency(freq: Long) {
        dataStore.edit { prefs ->
            prefs[SYNC_FREQUENCY] = freq
        }
    }

    override suspend fun getSyncFrequency(): Long {
        val stored = dataStore.data.map { it[SYNC_FREQUENCY] ?: DEFAULT_SYNC_FREQUENCY }.first()
        return runCatching { stored }
            .getOrDefault(DEFAULT_SYNC_FREQUENCY)
    }

    // ------------------ Haptics ------------------
    override val isHapticsEnabledFlow: Flow<Boolean> = dataStore.data
        .map { prefs ->
            val stored = prefs[IS_HAPTICS_ENABLED] ?: ""
            runCatching { stored.toBoolean() }
                .getOrDefault(DEFAULT_HAPTICS_ENABLED)
        }

    override suspend fun setHapticsEnabled(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[IS_HAPTICS_ENABLED] = enabled.toString()
        }
    }

    override suspend fun getHapticsEnabled(): Boolean {
        val stored = dataStore.data.map { it[IS_HAPTICS_ENABLED] ?: "" }.first()
        return runCatching { stored.toBoolean() }
            .getOrDefault(DEFAULT_HAPTICS_ENABLED)
    }

    // ------------------ Last Sync Time ------------------
    override val lastSyncTimeFlow: Flow<Long> = dataStore.data
        .map { prefs ->
            prefs[LAST_SYNC_TIME] ?: DEFAULT_LAST_SYNC_TIME
        }

    override suspend fun updateLastSyncTime(lastSync: Long) {
        dataStore.edit { prefs ->
            prefs[LAST_SYNC_TIME] = lastSync
        }
    }

    override suspend fun getLastSyncTime(): Long {
        return dataStore.data.map { it[LAST_SYNC_TIME] ?: DEFAULT_LAST_SYNC_TIME }.first()
    }

    // ------------------ Language ------------------
    override val selectedLanguageFlow: Flow<SupportedLanguage> = dataStore.data
        .map { prefs ->
            val stored = prefs[LANGUAGE_KEY] ?: ""
            runCatching { SupportedLanguage.valueOf(stored) }
                .getOrDefault(DEFAULT_LANGUAGE)
        }

    override suspend fun setLanguage(language: SupportedLanguage) {
        dataStore.edit { prefs ->
            prefs[LANGUAGE_KEY] = language.name
        }
    }

    override suspend fun getLanguage(): SupportedLanguage {
        val stored = dataStore.data.map { it[LANGUAGE_KEY] ?: "" }.first()
        return runCatching { SupportedLanguage.valueOf(stored) }
            .getOrDefault(DEFAULT_LANGUAGE)
    }

    // ------------------ DarkMode ------------------
    override val isDarkModeEnabledFlow: Flow<Boolean> = dataStore.data
        .map { prefs ->
            val stored = prefs[DARK_MODE_KEY] ?: DEFAULT_DARK_MODE
            runCatching { stored }.getOrDefault(DEFAULT_DARK_MODE)
        }

    override suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }

    override suspend fun getDarkMode(): Boolean {
        val stored = dataStore.data.first()[DARK_MODE_KEY] ?: DEFAULT_DARK_MODE
        return runCatching { stored }.getOrDefault(DEFAULT_DARK_MODE)
    }


    companion object {
        private const val DEFAULT_SYNC_FREQUENCY = 6 * 60 * 60 * 1000L
        private const val DEFAULT_HAPTICS_ENABLED = true
        private const val DEFAULT_LAST_SYNC_TIME = -1L
        private val DEFAULT_LANGUAGE = SupportedLanguage.RUSSIAN
        private const val DEFAULT_DARK_MODE = false
    }
}
