package ru.ttb220.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implements preferences management. Not used yet
 */
class PreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun getActiveAccountId(): Flow<Int> =
        dataStore.data
            .map { preferences -> preferences[ACTIVE_ACCOUNT_ID_KEY] ?: -1 }

    fun setActiveAccountId(id: Int): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences[ACTIVE_ACCOUNT_ID_KEY] = id
        }
        emit(Unit)
    }

    fun isDarkModeEnabled(): Flow<Boolean> =
        dataStore.data
            .map { preferences -> preferences[DARK_MODE_ENABLED_KEY] ?: false }

    fun setDarkModeEnabled(darkModeEnabled: Boolean): Flow<Unit> = flow {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_ENABLED_KEY] = darkModeEnabled
        }
        emit(Unit)
    }

    companion object {
        private val ACTIVE_ACCOUNT_ID_KEY = intPreferencesKey("active_account_id")
        private val DARK_MODE_ENABLED_KEY = booleanPreferencesKey("dark_mode_enabled")
    }
}