package com.example.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

class App : Application() {

    companion object {
        const val PREFS_NAME = "app_theme"
        const val DARK_THEME_KEY = "dark_theme"
    }

    var darkTheme = false
    private lateinit var sPref: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        darkTheme = sPref.getBoolean(DARK_THEME_KEY, false)

        switchTheme(darkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        sPref.edit { putBoolean(DARK_THEME_KEY, darkThemeEnabled) }

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}