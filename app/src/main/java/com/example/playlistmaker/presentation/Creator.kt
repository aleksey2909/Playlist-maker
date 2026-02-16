package com.example.playlistmaker.presentation

import android.content.Context
import com.example.playlistmaker.App
import com.example.playlistmaker.data.SearchHistory
import com.example.playlistmaker.data.TracksRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.domain.TracksInteractor
import com.example.playlistmaker.domain.usecase.TracksInteractorImpl
import com.example.playlistmaker.presentation.ui.SearchViewModel
import com.example.playlistmaker.presentation.ui.SettingsViewModel

object Creator {

    private val networkClient by lazy { RetrofitNetworkClient() }

    private fun provideTracksRepository() =
        TracksRepositoryImpl(networkClient)

    private fun provideTracksInteractor(): TracksInteractor =
        TracksInteractorImpl(provideTracksRepository())

    fun provideSearchViewModel(context: Context): SearchViewModel {
        val history = SearchHistory(context)
        val interactor = provideTracksInteractor()
        return SearchViewModel(interactor, history)
    }

    fun provideSettingsViewModel(context: Context): SettingsViewModel {
        val app = context.applicationContext as App
        return SettingsViewModel(app)
    }
}