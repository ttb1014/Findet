package ru.ttb220.data.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getActiveAccountId(): Flow<Int>

    fun setActiveAccountId(id: Int): Flow<Unit>

    fun isDarkModeEnabled(): Flow<Boolean>

    fun setDarkModeEnabled(darkModeEnabled: Boolean): Flow<Unit>
}