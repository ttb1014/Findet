package ru.ttb220.data.api.sync

import kotlinx.coroutines.flow.Flow

interface SyncManager {
    val isSyncing: Flow<Boolean>

    fun requestSync()
}
