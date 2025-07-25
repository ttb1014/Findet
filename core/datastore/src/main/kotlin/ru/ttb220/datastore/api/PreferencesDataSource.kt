package ru.ttb220.datastore.api

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
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
}