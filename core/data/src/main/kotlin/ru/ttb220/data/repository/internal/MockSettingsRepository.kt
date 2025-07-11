package ru.ttb220.data.repository.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.ttb220.data.repository.SettingsRepository
import ru.ttb220.mock.mockActiveAccountId
import ru.ttb220.mock.mockIsDarkThemeEnabled
import javax.inject.Inject

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