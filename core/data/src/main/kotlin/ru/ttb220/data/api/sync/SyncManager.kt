package ru.ttb220.data.api.sync

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface SyncManager {

    val isSyncing: Flow<Boolean>

    fun requestSync()

    suspend fun getLastSyncTime(): Instant

    val lastSyncTimeFlow: Flow<Instant>

    fun init()
}
