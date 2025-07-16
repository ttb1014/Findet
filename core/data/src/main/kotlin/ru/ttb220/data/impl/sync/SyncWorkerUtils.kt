package ru.ttb220.data.impl.sync

import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.NetworkType
import kotlin.reflect.KClass

internal const val WORKER_CLASS_NAME_KEY = "RouterWorkerDelegateClassName"

internal val SyncConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

internal fun KClass<out CoroutineWorker>.delegatedData() =
    Data.Builder()
        .putString(WORKER_CLASS_NAME_KEY, qualifiedName)
        .build()
