package ru.ttb220.data.api

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

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
}