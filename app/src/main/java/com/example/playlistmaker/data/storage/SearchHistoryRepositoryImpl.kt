package com.example.playlistmaker.data.storage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit
import com.example.playlistmaker.data.dto.TrackDto
import com.example.playlistmaker.data.dto.TrackMapper
import com.example.playlistmaker.domain.models.Track
import com.example.playlistmaker.domain.repository.SearchHistoryRepository

const val KEY = "tracks"

class SearchHistoryRepositoryImpl(
    context: Context,
    private val mapper: TrackMapper = TrackMapper()
) : SearchHistoryRepository {

    private val sPref = context.getSharedPreferences("search_history", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val maxSize = 10

    override fun getHistoryList(): List<Track> {
        val json = sPref.getString(KEY, null) ?: return emptyList()

        val type = object : TypeToken<List<TrackDto>>() {}.type
        val dtoList: List<TrackDto> = gson.fromJson(json, type)

        return dtoList.map { mapper.toDomain(it) }
    }

    override fun addTrack(track: Track) {
        val history = getHistoryList().toMutableList()

        history.removeAll { it.trackId == track.trackId }
        history.add(0, track)

        if (history.size > maxSize) {
            history.removeAt(history.lastIndex)
        }

        val dtoList = history.map { mapper.toDto(it) }
        val json = gson.toJson(dtoList)

        sPref.edit { putString(KEY, json) }
    }

    override fun clear() {
        sPref.edit { remove(KEY) }
    }
}