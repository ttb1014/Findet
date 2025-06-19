package ru.ttb220.data

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {

    val isOnline: Flow<Boolean>
}