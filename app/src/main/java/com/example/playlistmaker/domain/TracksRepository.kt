package com.example.playlistmaker.domain

import com.example.playlistmaker.domain.models.Track

interface TracksRepository {
    fun searchTracks(query: String): List<Track>
}