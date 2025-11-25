package com.example.androidframework

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Main Application class with Hilt support.
 */
@HiltAndroidApp
class AndroidFrameworkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any necessary components here
    }
}
