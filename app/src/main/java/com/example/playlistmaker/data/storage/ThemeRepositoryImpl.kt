package com.example.playlistmaker.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.playlistmaker.domain.repository.ThemeRepository

class ThemeRepositoryImpl(private val sharedPreferences: SharedPreferences) :
    ThemeRepository {

    override fun isDarkThemeEnabled(): Boolean {
        return sharedPreferences.getBoolean(DARK_THEME_KEY, false)
    }

    override fun setDarkThemeEnabled(enabled: Boolean) {
        sharedPreferences.edit { putBoolean(DARK_THEME_KEY, enabled) }
    }

    companion object {
        const val DARK_THEME_KEY = "dark_theme"
    }
}