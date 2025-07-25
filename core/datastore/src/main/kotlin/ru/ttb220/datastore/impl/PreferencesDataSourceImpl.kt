package ru.ttb220.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.ttb220.datastore.api.PreferencesDataSource
import ru.ttb220.datastore.impl.DataStoreKeys.SYNC_FREQUENCY
import ru.ttb220.datastore.impl.DataStoreKeys.THEME_STATE
import ru.ttb220.datastore.impl.DataStoreKeys.IS_HAPTICS_ENABLED
import ru.ttb220.model.ThemeState
import javax.inject.Inject

class PreferencesDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesDataSource {

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
        val stored = dataStore.data.first()[THEME_STATE] ?: ""
        return runCatching { ThemeState.valueOf(stored) }
            .getOrDefault(ThemeState.ORIGINAL)
    }

    override val syncFrequencyFlow: Flow<Long> = dataStore.data
        .map { prefs ->
            prefs[SYNC_FREQUENCY]?.toLong() ?: DEFAULT_SYNC_FREQUENCY
        }

    override suspend fun setSyncFrequency(freq: Long) {
        dataStore.edit { prefs ->
            prefs[SYNC_FREQUENCY] = freq.toString()
        }
    }

    override suspend fun getSyncFrequency(): Long {
        val prefs = dataStore.data.first()
        return prefs[SYNC_FREQUENCY]?.toLong() ?: DEFAULT_SYNC_FREQUENCY
    }

    override val isHapticsEnabledFlow: Flow<Boolean> = dataStore.data
        .map { prefs ->
            prefs[IS_HAPTICS_ENABLED]?.toBoolean() ?: DEFAULT_HAPTICS_ENABLED
        }

    override suspend fun setHapticsEnabled(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[IS_HAPTICS_ENABLED] = enabled.toString()
        }
    }

    override suspend fun getHapticsEnabled(): Boolean {
        val prefs = dataStore.data.first()
        return prefs[IS_HAPTICS_ENABLED]?.toBoolean() ?: DEFAULT_HAPTICS_ENABLED
    }

    companion object {
        // TODO: do we really need to specify default sync freq there? 
        private const val DEFAULT_SYNC_FREQUENCY = 6 * 60 * 60 * 1000L
        private const val DEFAULT_HAPTICS_ENABLED = true
    }
}