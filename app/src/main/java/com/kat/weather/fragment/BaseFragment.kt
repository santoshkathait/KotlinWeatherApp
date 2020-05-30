package com.kat.weather.fragment

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.kat.weather.R
import com.kat.weather.network.ConnectivityReceiver
import kotlinx.android.synthetic.main.main_fragment.*

/**
 * Base class  for Fragment
 */
open class BaseFragment : Fragment() {

    private var mSnackBar: Snackbar? = null
    private var connectivityReceiver: ConnectivityReceiver = ConnectivityReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.registerReceiver(
            connectivityReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    /**
     * Display message based on availibity of network
     */
    fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            stopSwipeRefresh()
            val messageToUser = getString(R.string.offline_message)
            val mSnackBar = Snackbar.make(
                activity!!.findViewById(R.id.container),
                messageToUser, Snackbar.LENGTH_LONG
            )
            mSnackBar.show()
        } else {
            mSnackBar?.dismiss()
        }
    }

    /**
     * Stop the swipe refresh loader
     */
    fun stopSwipeRefresh() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isEnabled = true
            swipeRefreshLayout.isRefreshing = false
        }
    }

    /**
     * Start the swipe refresh loader
     */
    fun startSwipeRefresh() {
        if (!swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.post {
                swipeRefreshLayout.isEnabled = false
                swipeRefreshLayout.isRefreshing = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(connectivityReceiver)
    }
}