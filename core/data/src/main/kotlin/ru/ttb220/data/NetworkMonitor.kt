package ru.ttb220.data

import kotlinx.coroutines.flow.Flow

/**
 * indicates whether a phone is connected to the internet
 */
interface NetworkMonitor {

    val isOnline: Flow<Boolean>
}