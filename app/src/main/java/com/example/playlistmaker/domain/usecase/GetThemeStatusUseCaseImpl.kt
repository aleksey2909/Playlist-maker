package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.repository.ThemeRepository

class GetThemeStatusUseCaseImpl(private val repository: ThemeRepository): GetThemeStatusUseCase {
    override fun execute(): Boolean {
        return repository.isDarkThemeEnabled()
    }
}