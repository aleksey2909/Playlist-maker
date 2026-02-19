package com.example.playlistmaker.app.di

import android.content.Context
import com.example.playlistmaker.data.storage.SearchHistoryRepositoryImpl
import com.example.playlistmaker.data.repository.TracksRepositoryImpl
import com.example.playlistmaker.data.network.RetrofitNetworkClient
import com.example.playlistmaker.data.storage.ThemeRepositoryImpl
import com.example.playlistmaker.domain.interactors.SearchHistoryInteractorImpl
import com.example.playlistmaker.domain.interactors.SearchHistoryInteractor
import com.example.playlistmaker.data.interactors.TracksInteractorImpl
import com.example.playlistmaker.domain.usecase.GetThemeStatusUseCase
import com.example.playlistmaker.domain.usecase.SwitchThemeUseCase
import com.example.playlistmaker.presentation.ui.SearchViewModel
import com.example.playlistmaker.presentation.ui.SettingsViewModel
import java.util.concurrent.Executors

object Creator {

    private val networkClient by lazy {
        RetrofitNetworkClient()
    }

    private val tracksRepository by lazy {
        TracksRepositoryImpl(networkClient)
    }

    private val executor by lazy {
        Executors.newFixedThreadPool(3)
    }

    private val tracksInteractor by lazy {
        TracksInteractorImpl(tracksRepository, executor)
    }

    private fun provideSearchHistoryInteractor(context: Context): SearchHistoryInteractor {
            val repo = SearchHistoryRepositoryImpl(context)
            return SearchHistoryInteractorImpl(repo)
    }

    fun provideSearchViewModel(context: Context): SearchViewModel {
            val historyInteractor = provideSearchHistoryInteractor(context)
            return SearchViewModel(tracksInteractor, historyInteractor)
    }


    fun provideSettingsViewModel(context: Context): SettingsViewModel {
        val prefs = context.getSharedPreferences("dark_theme", Context.MODE_PRIVATE)
        val themeRepository = ThemeRepositoryImpl(prefs)
        val getThemeStatusUseCase = GetThemeStatusUseCase(themeRepository)
        val switchThemeUseCase = SwitchThemeUseCase(themeRepository)
        return SettingsViewModel(getThemeStatusUseCase, switchThemeUseCase)
    }
}