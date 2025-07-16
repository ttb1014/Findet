package ru.ttb220.data.impl.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject

object SyncWorkerFactory : WorkerFactory() {

    lateinit var assistedFactory: SyncWorker.Factory

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): CoroutineWorker {
        return assistedFactory.create(appContext, workerParameters)
    }
}