package com.example.playlistmaker.data.dto

import com.example.playlistmaker.domain.models.Track

class TrackMapper{
    fun toDomain(dto: TrackDto): Track =
        Track(
            trackId = dto.trackId,
            trackName = dto.trackName,
            collectionName = dto.collectionName,
            releaseDate = dto.releaseDate,
            primaryGenreName = dto.primaryGenreName,
            country = dto.country,
            artistName = dto.artistName,
            trackTimeMillis = dto.trackTimeMillis,
            artworkUrl100 = dto.artworkUrl100,
            previewUrl = dto.previewUrl
        )
}