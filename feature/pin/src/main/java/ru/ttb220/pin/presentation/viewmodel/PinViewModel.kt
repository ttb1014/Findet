package ru.ttb220.pin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ttb220.data.api.SettingsRepository
import javax.inject.Inject

class PinViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    fun setupPinCode(pin: Int) {
        viewModelScope.launch {
            settingsRepository.setPinCode(pin)
        }
    }

    fun onPinEntered(pin: Int, callback: () -> Unit) {
        if (settingsRepository.verifyPinCode(pin))
            callback()
    }
}