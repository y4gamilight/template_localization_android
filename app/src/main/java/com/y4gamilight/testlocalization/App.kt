package com.y4gamilight.testlocalization

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.y4gamilight.testlocalization.ui.main.LocaleManager

open class App: Application() {
    val TAG = "TextLocalization";

    lateinit var localeManager: LocaleManager
    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        base?.let {context ->
            localeManager = LocaleManager(context)
        }
        super.attachBaseContext(base)
        Log.d(TAG, "attachBaseContext");
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
    }
}