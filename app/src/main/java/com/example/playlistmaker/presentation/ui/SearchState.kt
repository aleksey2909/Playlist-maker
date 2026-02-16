package com.example.playlistmaker.presentation.ui

import com.example.playlistmaker.domain.models.Track

sealed class SearchState {
    object Loading : SearchState()
    data class Content(val tracks: List<Track>) : SearchState()
    object Empty : SearchState()
    object Error : SearchState()
    data class History(val tracks: List<Track>) : SearchState()
}