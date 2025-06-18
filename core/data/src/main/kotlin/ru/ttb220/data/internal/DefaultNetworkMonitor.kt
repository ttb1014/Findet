package ru.ttb220.data.internal

import android.app.Service
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.ttb220.data.NetworkMonitor
import javax.inject.Inject

class DefaultNetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkMonitor {

    override val isOnline: Flow<Boolean> = flow {
        val manager = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (manager == null) {
            emit(false)
            return@flow
        }

        val network = manager.activeNetwork ?: run {
            emit(false)
            return@flow
        }

        val capabilities = manager.getNetworkCapabilities(network) ?: run {
            emit(false)
            return@flow
        }

        emit(capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
    }
}