package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.repository.ThemeRepository

interface GetThemeStatusUseCase {
    fun execute(): Boolean
}