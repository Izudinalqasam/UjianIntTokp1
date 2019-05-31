package com.tokopedia.testproject.problems.news.helper

import android.content.Context
import android.net.ConnectivityManager

object CommonHelper {

    @JvmStatic
    fun getConnectivityStatus(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetworkInfo = cm.activeNetworkInfo
        var statusNetwork = 0

        activeNetworkInfo?.let {
            statusNetwork = when(activeNetworkInfo.type){
                ConnectivityManager.TYPE_WIFI -> 1
                ConnectivityManager.TYPE_MOBILE -> 2
                else -> 0
            }
        }

        return statusNetwork
    }
}