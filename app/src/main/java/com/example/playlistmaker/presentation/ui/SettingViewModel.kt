package com.example.playlistmaker.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.App

class SettingsViewModel(private val app: App) : ViewModel() {

    private val _darkTheme = MutableLiveData<Boolean>()
    val darkTheme: LiveData<Boolean> = _darkTheme

    init {
        _darkTheme.value = app.darkTheme
    }

    fun onThemeSwitched(enabled: Boolean) {
        app.switchTheme(enabled)
        _darkTheme.value = enabled
    }
}