package ru.ttb220.database.util

interface SyncableEntity : Timestamped {

    val synced: Boolean
}