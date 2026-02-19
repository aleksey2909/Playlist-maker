package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.repository.ThemeRepository

class GetThemeStatusUseCase(private val repository: ThemeRepository) {
    fun execute(): Boolean {
        return repository.isDarkThemeEnabled()
    }
}