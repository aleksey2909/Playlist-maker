package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.TracksRepository
import com.example.playlistmaker.domain.models.Track

class SearchTracksUseCase(private val repository: TracksRepository) {
    fun execute(query: String): List<Track> {
        return repository.searchTracks(query)
    }
}