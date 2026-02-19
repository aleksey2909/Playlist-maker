package com.example.playlistmaker.presentation.ui

import com.example.playlistmaker.domain.models.Track

fun interface OnItemClickListener {
    fun onItemClick(item: Track)
}