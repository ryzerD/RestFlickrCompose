package com.example.restflickrcompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class FlickrApp : Application() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor
    override fun onCreate() {
        super.onCreate()
        networkMonitor.registerNetworkCallback()
    }
}