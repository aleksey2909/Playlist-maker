package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.repository.ThemeRepository

class SwitchThemeUseCaseImpl(private val themeRepository: ThemeRepository): SwitchThemeUseCase {

    override fun execute(darkThemeEnabled: Boolean) {
        themeRepository.setDarkThemeEnabled(darkThemeEnabled)
    }

    override fun isDarkThemeEnabled(): Boolean {
        return themeRepository.isDarkThemeEnabled()
    }
}