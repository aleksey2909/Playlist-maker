package com.example.playlistmaker.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.data.storage.ThemeRepositoryImpl
import com.example.playlistmaker.domain.repository.ThemeRepository
import com.example.playlistmaker.domain.usecase.SwitchThemeUseCase

class App : Application() {

    private lateinit var themeRepository: ThemeRepository
    private lateinit var switchThemeUseCase: SwitchThemeUseCase

    override fun onCreate() {
        super.onCreate()

        val sharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        themeRepository = ThemeRepositoryImpl(sharedPrefs)
        switchThemeUseCase = SwitchThemeUseCase(themeRepository)

        val isDarkTheme = switchThemeUseCase.isDarkThemeEnabled()
        applyTheme(isDarkTheme)
    }

    fun switchTheme(darkThemeEnabled: Boolean) {
        switchThemeUseCase.execute(darkThemeEnabled)
        applyTheme(darkThemeEnabled)
    }

    private fun applyTheme(darkThemeEnabled: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    companion object {
        const val PREFS_NAME = "dark_theme"
    }
}