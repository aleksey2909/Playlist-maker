package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.repository.TracksRepository
import com.example.playlistmaker.domain.models.Track

class SearchTracksUseCaseImpl(private val repository: TracksRepository): SearchTracksUseCase {
    override fun execute(query: String): List<Track> {
        return repository.searchTracks(query)
    }
}