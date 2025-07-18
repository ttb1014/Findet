package ru.ttb220.sync

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager

object SyncInitializer {

    /**
     * initializes one-shot and periodic (each 6 hours) sync work
     */
    fun initialize(context: Context) {
        Log.d(SYNC_WORK_NAME, "Sync work initializing...")

        WorkManager.getInstance(context).apply {
            // Run sync on app startup and ensure only one sync worker runs at any time
            enqueueUniqueWork(
                uniqueWorkName = SYNC_WORK_NAME,
                existingWorkPolicy = ExistingWorkPolicy.KEEP,
                request = SyncWorker.startUpSyncWork(),
            )

            enqueueUniquePeriodicWork(
                uniqueWorkName = SYNC_WORK_NAME,
                existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
                request = SyncWorker.periodicSyncWork()
            )
        }
    }
}
