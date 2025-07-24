package ru.ttb220.datastore.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.ttb220.datastore.api.PreferencesDataSource
import ru.ttb220.model.ThemeState
import javax.inject.Inject

class PreferencesDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesDataSource {

    override val themeState: Flow<ThemeState> = dataStore.data
        .map { preferences ->
            val value = preferences[DataStoreKeys.THEME_STATE]
            runCatching { ThemeState.valueOf(value ?: "") }
                .getOrDefault(ThemeState.ORIGINAL)
        }

    override suspend fun setThemeState(themeState: ThemeState) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.THEME_STATE] = themeState.name
        }
    }
}