package ru.ttb220.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.mock.mockActiveAccountId
import ru.ttb220.mock.mockIsDarkThemeEnabled
import javax.inject.Inject

// emits mock active account ID
class MockSettingsRepository @Inject constructor() : SettingsRepository {
    override fun getActiveAccountId(): Flow<Int> = flow {
        emit(mockActiveAccountId)
    }

    override fun setActiveAccountId(id: Int): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override fun isDarkModeEnabled(): Flow<Boolean> = flow {
        emit(mockIsDarkThemeEnabled)
    }

    override fun setDarkModeEnabled(darkModeEnabled: Boolean): Flow<Unit> {
        TODO("Not yet implemented")
    }
}