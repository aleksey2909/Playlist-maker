package com.example.playlistmaker.presentation.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.data.SearchHistory
import com.example.playlistmaker.domain.TracksInteractor
import com.example.playlistmaker.domain.models.Track

class SearchViewModel(
    private val interactor: TracksInteractor,
    private val searchHistory: SearchHistory
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
                searchHistory.clear()
                showHistoryOrEmpty()
            }
            else -> Unit
        }
    }

    fun onTrackClicked(track: Track) {
        searchHistory.addTrack(track)
    }

    fun restoreState() {
        if (searchQuery.isBlank()) {
            showHistoryOrEmpty()
        } else {
            performSearch()
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
        val history = searchHistory.getHistoryList()
        if (history.isEmpty()) {
            _state.postValue(SearchState.Empty)
        } else {
            _state.postValue(SearchState.History(history))
        }
    }

    override fun onCleared() {
        handler.removeCallbacks(searchRunnable)
        super.onCleared()
    }
}