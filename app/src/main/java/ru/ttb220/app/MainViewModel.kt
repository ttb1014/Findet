package ru.ttb220.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    init {
        viewModelScope.launch {
            delay(DEFAULT_SPLASH_SCREEN_DELAY)
            _isReady.value = true
        }
    }

    companion object {
        private const val DEFAULT_SPLASH_SCREEN_DELAY = 1000L
    }
}