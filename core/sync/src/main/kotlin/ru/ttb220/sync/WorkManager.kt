package ru.ttb220.sync

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.sync.SyncManager
import ru.ttb220.sync.di.SyncScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkManager @Inject constructor(
    private val context: Context,
    private val timeProvider: TimeProvider,
    private val settingsRepository: SettingsRepository,
    @SyncScope
    private val syncScope: CoroutineScope,
) : SyncManager {

    private val workManager get() = WorkManager.getInstance(context)

    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context).getWorkInfosForUniqueWorkFlow(SYNC_WORK_NAME)
            .onEach { workInfos ->
                val succeeded = workInfos.any { it.state == WorkInfo.State.SUCCEEDED }
                if (succeeded) {
                    settingsRepository.setLastSyncTime(timeProvider.now().toEpochMilliseconds())
                }
            }
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override fun requestSync() {
        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            SyncWorker.Companion.startUpSyncWork(),
        )
    }

    override suspend fun getLastSyncTime(): Instant = coroutineScope {
        val lastSyncTimeLong = settingsRepository.getLastSyncTime()
        Instant.fromEpochMilliseconds(lastSyncTimeLong)
    }

    override val lastSyncTimeFlow: Flow<Instant> =
        settingsRepository.getLastSyncFlow().map {
            Instant.fromEpochMilliseconds(it)
        }

    private fun observeAndReschedulePeriodicSync() {
        syncScope.launch {
            settingsRepository.getSyncFrequencyFlow()
                .distinctUntilChanged()
                .collectLatest { newFrequency ->
                    reschedulePeriodicSync(newFrequency)
                }
        }
    }

    private fun reschedulePeriodicSync(newFrequency: Long) {
        workManager.cancelUniqueWork(SYNC_WORK_NAME)

        workManager.enqueueUniquePeriodicWork(
            SYNC_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            SyncWorker.periodicSyncWork(newFrequency)
        )
    }

    override fun init() {
        observeAndReschedulePeriodicSync()
        requestSync()
    }

    companion object {

        private val DEFAULT_LAST_SYNC_TIME = Instant.DISTANT_PAST
    }
}
