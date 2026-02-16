package com.example.playlistmaker.domain

import com.example.playlistmaker.domain.models.Track

fun interface OnItemClickListener {
    fun onItemClick(item: Track)
}