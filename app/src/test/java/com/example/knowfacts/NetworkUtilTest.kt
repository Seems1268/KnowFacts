package com.example.knowfacts

import android.content.Context
import android.net.ConnectivityManager
import androidx.test.core.app.ApplicationProvider
import com.example.knowfacts.network.NetworkUtil
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Created by Seema Savadi on 01/09/20.
 */

@RunWith(RobolectricTestRunner::class)
class NetworkUtilTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
    }


    @Test
    fun test_internetConnectivity() {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        val isConnected = connectivityManager?.let {
            with(it.activeNetworkInfo) {
                this != null && isConnected
            }
        }

        isConnected?.let { connected ->
            if (connected) {
                assertTrue("Connected to Internet", NetworkUtil.isInternetAvailable(context))
            } else {
                assertTrue("Not connected to Internet", !NetworkUtil.isInternetAvailable(context))
            }
        }

    }
}