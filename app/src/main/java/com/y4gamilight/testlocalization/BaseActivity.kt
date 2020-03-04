package com.y4gamilight.testlocalization

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open abstract class BaseActivity: AppCompatActivity() {
    val TAG = "BaseActivity"
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(App.localeManager.setLocale(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resetActivityTitle()
    }

    private fun resetActivityTitle() {
        try {
            val info = packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
            if (info.labelRes != 0) {
                setTitle(info.labelRes)
            }
        } catch (e : PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }
}