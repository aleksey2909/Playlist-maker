package com.example.playlistmaker.data.network

import com.example.playlistmaker.data.dto.Response
import com.example.playlistmaker.data.dto.TrackDto

data class TracksSearchResponse(val resultCount: Int, val results: List<TrackDto>) : Response()