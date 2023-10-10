package com.example.shoppinglist.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.activity.ComponentActivity

class InternetManager () {
    companion object {
        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(ComponentActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetwork

            return activeNetworkInfo != null
        }
    }
}