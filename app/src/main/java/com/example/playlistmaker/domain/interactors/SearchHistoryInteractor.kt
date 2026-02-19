package com.example.playlistmaker.domain.interactors

import com.example.playlistmaker.domain.models.Track

interface SearchHistoryInteractor {
    fun getHistory(): List<Track>
    fun addTrack(track: Track)
    fun clear()
}