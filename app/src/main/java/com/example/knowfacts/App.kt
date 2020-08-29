package com.example.knowfacts

/**
 * Created by Seema Savadi on 28/08/20.
 */

import android.app.Application
import timber.log.Timber

/**
 * Custom application class to handle app level settings.
 */

class App : Application() {

    override fun onCreate() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        super.onCreate()
        Timber.i("OnCreate")

    }
}