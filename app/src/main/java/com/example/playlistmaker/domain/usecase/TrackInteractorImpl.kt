package com.example.playlistmaker.domain.usecase

import com.example.playlistmaker.domain.TracksInteractor
import com.example.playlistmaker.domain.TracksRepository

class TracksInteractorImpl(private val repository: TracksRepository) : TracksInteractor {

    override fun searchTracks(expression: String, consumer: TracksInteractor.TracksConsumer) {
        Thread {
            val result = repository.searchTracks(expression)
            consumer.consume(result)
        }.start()
    }
}