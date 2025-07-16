package ru.ttb220.data.impl.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters

class DelegatingWorker(
    context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    private val workerClassName =
        workerParams.inputData.getString(WORKER_CLASS_NAME_KEY) ?: ""

    private val delegateWorker = SyncWorkerFactory.createWorker(
        context,
        workerClassName,
        workerParams
    )

    override suspend fun getForegroundInfo(): ForegroundInfo =
        delegateWorker.getForegroundInfo()

    override suspend fun doWork(): Result =
        delegateWorker.doWork()
}
