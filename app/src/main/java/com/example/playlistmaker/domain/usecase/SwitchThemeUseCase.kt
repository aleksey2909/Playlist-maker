package com.example.playlistmaker.domain.usecase

interface SwitchThemeUseCase {
    fun execute(darkThemeEnabled: Boolean)
    fun isDarkThemeEnabled(): Boolean
}