package com.example.playlistmaker.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.usecase.GetThemeStatusUseCase
import com.example.playlistmaker.domain.usecase.SwitchThemeUseCase

class SettingsViewModel(
    private val getThemeStatusUseCase: GetThemeStatusUseCase,
    private val switchThemeUseCase: SwitchThemeUseCase
) : ViewModel() {

    private val _darkTheme = MutableLiveData<Boolean>()
    val darkTheme: LiveData<Boolean> = _darkTheme

    init {
        _darkTheme.value = getThemeStatusUseCase.execute()
    }

    fun switchTheme(enabled: Boolean) {
        switchThemeUseCase.execute(enabled)
        _darkTheme.value = enabled
    }
}