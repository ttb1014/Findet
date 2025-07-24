package ru.ttb220.datastore.api

import kotlinx.coroutines.flow.Flow
import ru.ttb220.model.ThemeState

interface PreferencesDataSource {

    val themeState: Flow<ThemeState>

    suspend fun setThemeState(themeState: ThemeState)
}