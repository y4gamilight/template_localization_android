package com.y4gamilight.testlocalization.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*
import android.os.LocaleList
import androidx.annotation.RequiresApi
import android.os.Build.VERSION_CODES.JELLY_BEAN_MR1
import android.os.Build.VERSION_CODES.N
import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build


class LocaleManager {

    private val keyLocale = "TestLocalizationPrefs"
    constructor(context: Context) {
        prefs = context.getSharedPreferences(keyLocale, Context.MODE_PRIVATE)
    }

    fun setLocale(c: Context?): Context? {
        return updateResources(c, language)
    }

    fun setNewLocale(c: Context, language: String): Context? {
        persistLanguage(language)
        return updateResources(c, language)
    }
    private fun updateResources(context: Context?, language: String): Context? {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)

        context?.resources?.let {res ->
            val config = Configuration(res.configuration)

            if (Build.VERSION.SDK_INT >= N ) {
                setLocaleForApi24(config, locale)
                context = context?.createConfigurationContext(config)
            } else if (Build.VERSION.SDK_INT >= JELLY_BEAN_MR1) {
                config.setLocale(locale)
                context = context?.createConfigurationContext(config)
            } else {
                config.locale = locale
                res.updateConfiguration(config, res.displayMetrics)
            }
        }

        return context
    }

    @RequiresApi(api = N)
    private fun setLocaleForApi24(config: Configuration, target: Locale) {
        var set:MutableSet<Locale> = linkedSetOf()
        // bring the target locale to the front of the list
        set.add(target)

        val all = LocaleList.getDefault()
        for (i in 0 until all.size()) {
            // append other locales supported by the user
            set.add(all.get(i))
        }

        val locales = set.toTypedArray()
        config.setLocales(LocaleList(*locales))
    }

    @SuppressLint("ApplySharedPref")
    private fun persistLanguage(language: String) {
        // use commit() instead of apply(), because sometimes we kill the application process
        // immediately that prevents apply() from finishing
        prefs.edit().putString(LANGUAGE_KEY, language).commit()
    }


    fun getLocale(res: Resources): Locale {
        val config = res.getConfiguration()
        if (Build.VERSION.SDK_INT >= N) {
            return config.locales[0]
        } else {
            return config.locale
        }
    }

    companion object {
        lateinit var prefs: SharedPreferences
        val language: String
            get() = prefs.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH) ?: ""




        val LANGUAGE_ENGLISH = "en"
        val LANGUAGE_UKRAINIAN = "uk"
        val LANGUAGE_RUSSIAN = "ru"
        val LANGUAGE_VIETNAMESE = "vi"
        private val LANGUAGE_KEY = "language_key"

    }
}