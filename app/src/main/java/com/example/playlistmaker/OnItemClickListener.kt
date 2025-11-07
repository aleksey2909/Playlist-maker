package com.example.playlistmaker

import com.example.playlistmaker.model.Track

interface OnItemClickListener {
    fun onItemClick(item: Track)
}