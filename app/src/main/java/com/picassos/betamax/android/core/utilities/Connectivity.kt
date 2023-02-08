package com.picassos.betamax.android.core.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build

class Connectivity {
    private lateinit var wifiManager: WifiManager
    private lateinit var connectivityManager: ConnectivityManager

    companion object {
        val instance = Connectivity()
    }

    fun initializeWithApplicationContext (context: Context) {
        wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    fun isOnline(): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

    fun isVpnActive(): Boolean {
        var vpnStatus = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork: Network? = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            return capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
        }
        val networks: Array<Network> = connectivityManager.allNetworks
        for (i in networks.indices) {
            val caps = connectivityManager.getNetworkCapabilities(networks[i])
            if (caps!!.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                vpnStatus = true
                break
            }
        }
        return vpnStatus
    }
}