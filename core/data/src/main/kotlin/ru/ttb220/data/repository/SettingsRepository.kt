package ru.ttb220.data.repository

import kotlinx.coroutines.flow.Flow

/**
 * Each method provides either wrapped data or wrapped domain error. Emits single value.
 */
interface SettingsRepository {

    fun getActiveAccountId(): Flow<Int>

    fun setActiveAccountId(id: Int): Flow<Unit>

    fun isDarkModeEnabled(): Flow<Boolean>

    fun setDarkModeEnabled(darkModeEnabled: Boolean): Flow<Unit>
}