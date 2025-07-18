package ru.ttb220.data.api.sync

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

interface SyncManager {

    val isSyncing: Flow<Boolean>

    fun requestSync()

    // FIXME: remove
    val lastSyncTime: Instant

    val lastSyncTimeFlow: Flow<Instant>
}
