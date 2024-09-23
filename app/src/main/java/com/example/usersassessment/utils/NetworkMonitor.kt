package com.example.usersassessment.utils

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class NetworkMonitor(private val connectivityManager: ConnectivityManager) {
    private var callback: ConnectivityManager.NetworkCallback? = null

    private val _isNetworkConnected = MutableStateFlow(isNetworkAvailable())
    val isNetworkConnected: StateFlow<Boolean>
        get() = _isNetworkConnected

    init {
        _isNetworkConnected.subscriptionCount
            .map { count -> count > 0 }
            .distinctUntilChanged()
            .onEach { isActive ->
                if (isActive) subscribe() else unsubscribe()
            }
            .launchIn(MainScope())
    }

    private fun subscribe() {
        if (callback != null) return
        callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) = emitEvent(true)
            override fun onLost(network: Network) = emitEvent(false)
        }.also {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()
            connectivityManager.registerNetworkCallback(request, it)
        }
        emitEvent(isNetworkAvailable())
    }

    private fun unsubscribe() {
        if (callback == null) return
        callback?.run { connectivityManager.unregisterNetworkCallback(this) }
        callback = null
    }

    private fun emitEvent(isAvailable: Boolean) {
        _isNetworkConnected.value = isAvailable
        Log.d("NetworkMonitor", "Network state changed. isAvailable= $isAvailable")
    }

    fun isNetworkAvailable(): Boolean =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
}