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

    private val _isNetworkConnected = MutableStateFlow(false)
    val isNetworkConnected: StateFlow<Boolean>
        get() = _isNetworkConnected

    init {
//        checkNetworkState()

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
            override fun onLost(network: Network) {
                super.onLost(network)
                checkNetworkState()
            }
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                checkNetworkState()
            }
        }.also {
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()
            connectivityManager.registerNetworkCallback(request, it)
        }
    }

    private fun unsubscribe() {
        if (callback == null) return
        callback?.run { connectivityManager.unregisterNetworkCallback(this) }
        callback = null
    }

    private fun checkNetworkState() {
        emitEvent(isNetworkAvailable())
    }

    private fun emitEvent(isAvailable: Boolean) {
        _isNetworkConnected.value = isAvailable
        Log.d("NetworkMonitor", "Network state changed. isAvailable= $isAvailable")
    }

    private fun isNetworkAvailable(): Boolean {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}