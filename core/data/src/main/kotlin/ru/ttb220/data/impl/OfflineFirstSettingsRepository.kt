package ru.ttb220.data.impl

import androidx.room.concurrent.AtomicInt
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.data.impl.util.withExpBackoffRetry
import ru.ttb220.network.api.RemoteDataSource
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

// FIXME: impl with datastore
class OfflineFirstSettingsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : SettingsRepository, Syncable {

    private var isDarkModeEnabled: AtomicBoolean = AtomicBoolean(DEFAULT_DARK_THEME_MODE)
    private var activeAccountId: AtomicInt = AtomicInt(DEFAULT_ACCOUNT_ID)
    private var userId: AtomicInt = AtomicInt(DEFAULT_USER_ID)

    override fun getActiveAccountId(): Flow<Int> = flow {
        val storedActiveAccountId = activeAccountId.get()
        emit(activeAccountId.get())
    }

    override suspend fun setActiveAccountId(id: Int): Boolean {
        activeAccountId.set(id)
        return true
    }

    override fun isDarkModeEnabled(): Flow<Boolean> = flow {
        emit(isDarkModeEnabled.get())
    }

    override suspend fun setDarkModeEnabled(darkModeEnabled: Boolean): Boolean {
        isDarkModeEnabled.set(darkModeEnabled)
        return true
    }

    override fun getActiveUserId(): Flow<Int> = flow {
        val storedId = userId.get()
        emit(storedId)
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = coroutineScope {
        val remoteAccounts = flow { emit(remoteDataSource.getAllAccounts()) }
            .withExpBackoffRetry()
            .first()

        val accountJob = launch {
            val randomAccount = remoteAccounts
                .firstOrNull()
                ?.id ?: DEFAULT_ACCOUNT_ID
            activeAccountId.set(randomAccount)
        }

        val userJob = launch {
            val randomUserId = remoteAccounts
                .firstOrNull()
                ?.userId ?: DEFAULT_USER_ID
            userId.set(randomUserId)
        }

        joinAll(
            accountJob,
            userJob
        )

        true
    }

    companion object {
        // used as a fallback.
        // TODO: resolve fallback policy
        private const val DEFAULT_USER_ID = 54
        private const val DEFAULT_DARK_THEME_MODE = false
        private const val DEFAULT_ACCOUNT_ID = 430
    }
}