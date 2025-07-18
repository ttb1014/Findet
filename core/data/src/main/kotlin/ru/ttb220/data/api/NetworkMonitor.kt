package ru.ttb220.data.api

import kotlinx.coroutines.flow.Flow

/**
 * Indicates whether a phone is connected to the internet.
 * Emits values continuously on each change.
 */
interface NetworkMonitor {

    val isOnline: Flow<Boolean>
}