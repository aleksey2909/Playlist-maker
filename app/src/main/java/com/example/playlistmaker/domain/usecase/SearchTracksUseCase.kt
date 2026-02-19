package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.models.Track

interface SearchTracksUseCase {
    fun execute(query: String): List<Track>
}