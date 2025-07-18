package ru.ttb220.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

object SyncWorkerFactory : WorkerFactory() {

    lateinit var assistedFactory: SyncWorker.AssistedFactory

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): CoroutineWorker {
        return assistedFactory.create(appContext, workerParameters)
    }
}