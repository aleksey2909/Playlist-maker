package com.example.playlistmaker.presentation.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.interactors.SearchHistoryInteractor
import com.example.playlistmaker.domain.interactors.TracksInteractor
import com.example.playlistmaker.domain.models.Track

class SearchViewModel(
    private val interactor: TracksInteractor,
    private val searchHistoryInteractor: SearchHistoryInteractor
) : ViewModel() {

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val _state = MutableLiveData<SearchState>()
    val state: LiveData<SearchState> = _state

    private var searchQuery: String = ""

    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { performSearch() }

    fun onQueryChanged(query: String) {
        searchQuery = query
        if (query.isBlank()) {
            showHistoryOrEmpty()
        } else {
            debounceSearch()
        }
    }

    fun onActionButtonClicked() {
        when (_state.value) {
            is SearchState.Error -> performSearch()
            is SearchState.History -> {
                searchHistoryInteractor.clear()
                showHistoryOrEmpty()
            }
            else -> Unit
        }
    }

    fun onTrackClicked(track: Track) {
        searchHistoryInteractor.addTrack(track)
    }

    fun restoreState() {
        val history = searchHistoryInteractor.getHistory()

        _state.value = if (history.isNotEmpty()) {
            SearchState.History(history)
        } else {
            SearchState.Default
        }
    }

    private fun debounceSearch() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    private fun performSearch() {
        val query = searchQuery
        if (query.isBlank()) {
            showHistoryOrEmpty()
            return
        }

        _state.postValue(SearchState.Loading)

        interactor.searchTracks(query) { tracks ->
            if (tracks.isEmpty()) {
                _state.postValue(SearchState.Empty)
            } else {
                _state.postValue(SearchState.Content(tracks))
            }
        }
    }

    private fun showHistoryOrEmpty() {
        val history = searchHistoryInteractor.getHistory()
        if (history.isEmpty()) {
            _state.postValue(SearchState.Default)
        } else {
            _state.postValue(SearchState.History(history))
        }
    }

    override fun onCleared() {
        handler.removeCallbacks(searchRunnable)
        super.onCleared()
    }
}