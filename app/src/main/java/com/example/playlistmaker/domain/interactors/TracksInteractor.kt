package com.example.playlistmaker.domain.interactors

import com.example.playlistmaker.domain.models.Track

interface TracksInteractor {
    fun searchTracks(expression: String, consumer: TracksConsumer)

    fun interface TracksConsumer {
        fun consume(tracks: List<Track>)
    }
}