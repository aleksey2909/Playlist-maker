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

    fun toDto(track: Track): TrackDto =
        TrackDto(
            trackId = track.trackId,
            trackName = track.trackName,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            artistName = track.artistName,
            trackTimeMillis = track.trackTimeMillis,
            artworkUrl100 = track.artworkUrl100,
            previewUrl = track.previewUrl
        )
}