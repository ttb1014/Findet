package ru.ttb220.data.repository.internal

import kotlinx.coroutines.flow.Flow
import ru.ttb220.data.repository.SettingsRepository
import ru.ttb220.datastore.PreferencesDataSource
import javax.inject.Inject

class DataStoreSettingsRepository @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) : SettingsRepository {

    override fun getActiveAccountId(): Flow<Int> =
        preferencesDataSource.getActiveAccountId()

    override fun setActiveAccountId(id: Int): Flow<Unit> =
        preferencesDataSource.setActiveAccountId(id)

    override fun isDarkModeEnabled(): Flow<Boolean> =
        preferencesDataSource.isDarkModeEnabled()

    override fun setDarkModeEnabled(darkModeEnabled: Boolean): Flow<Unit> =
        preferencesDataSource.setDarkModeEnabled(darkModeEnabled)
}