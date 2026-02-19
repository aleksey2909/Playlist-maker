package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.repository.ThemeRepository

class SwitchThemeUseCase(private val themeRepository: ThemeRepository) {

    fun execute(darkThemeEnabled: Boolean) {
        themeRepository.setDarkThemeEnabled(darkThemeEnabled)
    }

    fun isDarkThemeEnabled(): Boolean {
        return themeRepository.isDarkThemeEnabled()
    }
}