package com.example.knowfacts.network

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Seema Savadi on 31/08/20.
 */

/**
 * This util class will check for internet availability.
 */
class NetworkUtil {

    companion object {

        fun isInternetAvailable(context: Context): Boolean {

            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

            connectivityManager?.let {
                with(it.activeNetworkInfo) {
                    return this != null && isConnected
                }
            }

            return false
        }
    }
}