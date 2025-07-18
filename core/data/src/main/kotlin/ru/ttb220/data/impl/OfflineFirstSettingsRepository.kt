package ru.ttb220.data.impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.data.impl.util.withExpBackoffRetry
import ru.ttb220.data.BuildConfig
import ru.ttb220.network.api.RemoteDataSource
import javax.inject.Inject

// FIXME: impl with datastore
class OfflineFirstSettingsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : SettingsRepository, Syncable {

    private val isDarkModeEnabled = MutableStateFlow(DEFAULT_DARK_THEME_MODE)
    private val activeAccountId = MutableStateFlow(DEFAULT_ACCOUNT_ID)
    private val userId = MutableStateFlow(DEFAULT_USER_ID)

    override fun getActiveAccountId(): Flow<Int> = activeAccountId

    override suspend fun setActiveAccountId(id: Int): Boolean {
        activeAccountId.value = id
        return true
    }

    override fun isDarkModeEnabled(): Flow<Boolean> = isDarkModeEnabled

    override suspend fun setDarkModeEnabled(darkModeEnabled: Boolean): Boolean {
        isDarkModeEnabled.value = darkModeEnabled
        return true
    }

    override fun getActiveUserId(): Flow<Int> = userId

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = coroutineScope {
        val remoteAccounts = flow { emit(remoteDataSource.getAllAccounts()) }
            .withExpBackoffRetry()
            .first()

        val accountJob = launch {
            val randomAccount = remoteAccounts
                .firstOrNull()
                ?.id ?: DEFAULT_ACCOUNT_ID
            activeAccountId.value = randomAccount
        }

        val userJob = launch {
            val randomUserId = remoteAccounts
                .firstOrNull()
                ?.userId ?: DEFAULT_USER_ID
            userId.value = randomUserId
        }

        joinAll(accountJob, userJob)

        true
    }

    companion object {
        // used as a fallback.
        // TODO: resolve fallback policy
        private val DEFAULT_USER_ID = BuildConfig.MOCK_USER_ID.toInt()
        private const val DEFAULT_DARK_THEME_MODE = false
        private val DEFAULT_ACCOUNT_ID = BuildConfig.MOCK_ACCOUNT_ID.toInt()
    }
}