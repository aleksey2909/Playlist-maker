package com.example.playlistmaker.data.interactors

import com.example.playlistmaker.domain.interactors.TracksInteractor
import com.example.playlistmaker.domain.repository.TracksRepository
import java.util.concurrent.ExecutorService

class TracksInteractorImpl(
    private val repository: TracksRepository,
    private val executor: ExecutorService
) : TracksInteractor {

    override fun searchTracks(expression: String, consumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            val result = repository.searchTracks(expression)
            consumer.consume(result)
        }
    }
}