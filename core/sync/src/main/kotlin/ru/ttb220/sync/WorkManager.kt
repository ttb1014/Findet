package ru.ttb220.sync

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Instant
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.sync.SyncManager
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkManager @Inject constructor(
    private val context: Context,
    private val timeProvider: TimeProvider,
) : SyncManager {

    override val isSyncing: Flow<Boolean> =
        WorkManager.getInstance(context).getWorkInfosForUniqueWorkFlow(SYNC_WORK_NAME)
            .onEach { workInfos ->
                val succeeded = workInfos.any { it.state == WorkInfo.State.SUCCEEDED }
                if (succeeded) {
                    _lastSyncTime.set(timeProvider.now())
                }
            }
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    // FIXME: move to datastore
    private var _lastSyncTime = AtomicReference<Instant>(DEFAULT_LAST_SYNC_TIME)

    override val lastSyncTime: Instant
        get() = _lastSyncTime.get()

    override fun requestSync() {
        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            SyncWorker.Companion.startUpSyncWork(),
        )
    }

    companion object {
        private val DEFAULT_LAST_SYNC_TIME = Instant.DISTANT_PAST
    }
}
