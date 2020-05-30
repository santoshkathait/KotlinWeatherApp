package com.kat.weather.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Broadcast reciever class for change in network
 */
open class ConnectivityReceiver : BroadcastReceiver() {

    var connectivityHelper: ConnectivityHelper = ConnectivityHelper()

    override fun onReceive(context: Context, arg1: Intent) {
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.onNetworkConnectionChanged(
                connectivityHelper.isConnectingToInternet(
                    context
                )
            )
        }
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null
    }
}