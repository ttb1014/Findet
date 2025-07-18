package ru.ttb220.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters

/**
 * Constructor is called from Android framework.
 * All work is delegated to WorkManager
 * @see WorkManager
 */
class DelegatingWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    private val workerClassName =
        workerParams.inputData.getString(WORKER_CLASS_NAME_KEY) ?: ""

    private val delegateWorker = SyncWorkerFactory.createWorker(
        appContext = context,
        workerClassName = workerClassName,
        workerParameters = workerParams
    )

    override suspend fun getForegroundInfo(): ForegroundInfo =
        delegateWorker.getForegroundInfo()

    override suspend fun doWork(): Result =
        delegateWorker.doWork()
}
