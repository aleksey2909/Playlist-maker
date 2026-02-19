package com.example.playlistmaker.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.domain.models.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit
import com.example.playlistmaker.domain.repository.SearchHistoryRepository

const val KEY = "tracks"

class SearchHistoryRepositoryImpl(context: Context): SearchHistoryRepository {

    private val sPref: SharedPreferences = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)

    private val gson = Gson()
    private val maxSize = 10

    override fun getHistoryList(): List<Track> {
        val json = sPref.getString(KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Track>>() {}.type
        return gson.fromJson(json, type)
    }

    override fun addTrack(track: Track) {
        val history = getHistoryList().toMutableList()
        if (history.any { it.trackId == track.trackId}){
            history.removeAll { it.trackId == track.trackId }
        }
        history.add(0, track)
        if (history.size > maxSize) {
            history.removeAt(history.lastIndex)
        }
        val json = gson.toJson(history)
        sPref.edit { putString(KEY, json) }
    }

    override fun clear() {
        sPref.edit {remove(KEY)}
    }
}