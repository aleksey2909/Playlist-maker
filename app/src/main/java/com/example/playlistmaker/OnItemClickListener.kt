package com.example.playlistmaker

import com.example.playlistmaker.model.Track

fun interface OnItemClickListener {
    fun onItemClick(item: Track)
}