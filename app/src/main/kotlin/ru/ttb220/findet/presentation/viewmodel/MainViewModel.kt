package ru.ttb220.findet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.ttb220.data.api.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Responsible for splash screen functionality
 */

@Singleton
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    val activeAccountId: StateFlow<Int?> =
        settingsRepository
            .getActiveAccountId()
            .stateIn(
                scope = viewModelScope,
                initialValue = null,
                started = SharingStarted.Companion.WhileSubscribed(DEFAULT_SUBSCRIBE_PERIOD),
            )

    fun activeLanguageFlow() = settingsRepository.activeLanguageFlow()

    init {
        viewModelScope.launch {
            delay(DEFAULT_SPLASH_SCREEN_DELAY)
            _isReady.value = true
        }
    }

    companion object {
        private const val DEFAULT_SPLASH_SCREEN_DELAY = 1000L
        private const val DEFAULT_SUBSCRIBE_PERIOD = 5_000L
    }
}