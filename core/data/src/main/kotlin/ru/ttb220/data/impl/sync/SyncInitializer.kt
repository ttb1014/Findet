package ru.ttb220.data.impl.sync

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager

internal const val SYNC_WORK_NAME = "SyncWork"

object Sync {
    // initializes one-shot sync work and periodic (each 6 hours) sync work

    fun initialize(context: Context) {
        Log.d(SYNC_WORK_NAME, "initializing!!")

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
