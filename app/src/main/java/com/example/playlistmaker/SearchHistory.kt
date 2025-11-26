package com.example.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.model.Track
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit

const val KEY = "tracks"

class SearchHistory(context: Context) {

    private val sPref: SharedPreferences = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)

    private val gson = Gson()
    private val maxSize = 10

    fun getHistoryList(): List<Track> {
        val json = sPref.getString(KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Track>>() {}.type
        return gson.fromJson(json, type)
    }

    fun addTrack(track: Track) {
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

    fun clear() {
        sPref.edit {remove(KEY)}
    }
}