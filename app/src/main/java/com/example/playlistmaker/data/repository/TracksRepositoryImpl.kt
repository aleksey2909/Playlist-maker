package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.network.NetworkClient
import com.example.playlistmaker.data.network.TracksSearchResponse
import com.example.playlistmaker.data.dto.TrackMapper
import com.example.playlistmaker.data.dto.TrackSearchRequest
import com.example.playlistmaker.domain.repository.TracksRepository
import com.example.playlistmaker.domain.models.Track

class TracksRepositoryImpl(private val networkClient: NetworkClient): TracksRepository {
    override fun searchTracks(query: String): List<Track> {
        val response = networkClient.doRequest(TrackSearchRequest(query))
        return if (response.resultCode == 200) {
            val body = response as TracksSearchResponse
            val mapper = TrackMapper()
            body.results.map { dto -> mapper.toDomain(dto) }
        } else {
            emptyList()
        }

    }
}