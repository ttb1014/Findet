package ru.ttb220.sync

import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.WorkInfo
import kotlin.reflect.KClass

internal const val WORKER_CLASS_NAME_KEY = "RouterWorkerDelegateClassName"

internal const val SYNC_WORK_NAME = "SyncWork"

/**
 * Cannot sync without internet
 */
internal val SyncConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

/**
 * Puts caller's class name to data bundle
 */
internal fun KClass<out CoroutineWorker>.delegatedData() =
    Data.Builder()
        .putString(WORKER_CLASS_NAME_KEY, qualifiedName)
        .build()

internal fun List<WorkInfo>.anyRunning() = any { it.state == WorkInfo.State.RUNNING }