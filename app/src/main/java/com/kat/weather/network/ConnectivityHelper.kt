package com.kat.weather.network

import android.content.Context
import android.net.ConnectivityManager

/**
 * Class to check the availibity of network
 */
class ConnectivityHelper {
    fun isConnectingToInternet(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager?.activeNetworkInfo?.isConnectedOrConnecting() ?: false
    }
}