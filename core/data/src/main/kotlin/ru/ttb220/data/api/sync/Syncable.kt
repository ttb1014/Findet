package ru.ttb220.data.api.sync

interface Syncable {
    suspend fun syncWith(synchronizer: Synchronizer): Boolean
}