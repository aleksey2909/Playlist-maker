package com.example.playlistmaker.domain.repository

import com.example.playlistmaker.domain.models.Track

interface SearchHistoryRepository {
    fun getHistoryList(): List<Track>
    fun addTrack(track: Track)
    fun clear()
}