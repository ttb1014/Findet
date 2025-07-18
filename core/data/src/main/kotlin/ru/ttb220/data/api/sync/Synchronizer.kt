package ru.ttb220.data.api.sync

interface Synchronizer {

    suspend fun Syncable.sync():Boolean = this@sync.syncWith(this@Synchronizer)
}