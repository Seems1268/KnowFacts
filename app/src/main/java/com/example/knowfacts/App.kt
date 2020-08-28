package com.example.knowfacts

import android.app.Application
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
        super.onCreate()

    }
}