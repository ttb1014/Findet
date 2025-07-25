package ru.ttb220.data.api

import kotlinx.coroutines.flow.Flow
import ru.ttb220.model.SupportedLanguage
import ru.ttb220.model.ThemeState

/**
 * Each method provides either wrapped data or wrapped domain error.
 * Emits single value.
 */
interface SettingsRepository {

    fun getActiveAccountId(): Flow<Int>

    suspend fun setActiveAccountId(id: Int): Boolean

    fun isDarkModeEnabled(): Flow<Boolean>

    suspend fun setDarkModeEnabled(darkModeEnabled: Boolean): Boolean

    fun getActiveUserId(): Flow<Int>

    suspend fun setThemeState(themeState: ThemeState)

    fun getThemeStateFlow(): Flow<ThemeState>

    suspend fun setHapticsEnabled(enabled: Boolean)

    fun getHapticsEnabledFlow(): Flow<Boolean>

    suspend fun setPinCode(pin: Int)

    fun verifyPinCode(pin: Int): Boolean

    fun isPinSetup(): Boolean

    suspend fun setSyncFrequency(freq: Long)

    fun getLastSyncFlow(): Flow<Long>

    suspend fun getLastSyncTime(): Long

    suspend fun setLastSyncTime(syncTime: Long)

    fun getSyncFrequencyFlow(): Flow<Long>

    fun activeLanguageFlow(): Flow<SupportedLanguage>
    suspend fun getActiveLanguage(): SupportedLanguage
    suspend fun setActiveLanguage(language: SupportedLanguage)
}