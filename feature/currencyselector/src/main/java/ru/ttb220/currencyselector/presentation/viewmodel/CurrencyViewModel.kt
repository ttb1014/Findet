package ru.ttb220.currencyselector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.ttb220.currencyselector.domain.SetActiveAccountCurrencyUseCase
import ru.ttb220.presentation.model.CurrencyData
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val setActiveAccountCurrencyUseCase: SetActiveAccountCurrencyUseCase,
) : ViewModel() {

    fun onCurrencyClick(currency: CurrencyData, afterChanged: () -> Unit) {
        viewModelScope.launch {
            setActiveAccountCurrencyUseCase(currency.toString()).collect {}
            afterChanged()
        }
    }
}