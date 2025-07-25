package ru.ttb220.account.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.ttb220.account.domain.AddAccountUseCase
import ru.ttb220.model.account.AccountBrief
import javax.inject.Inject

class AddAccountViewModel @Inject constructor(
    private val addAccountUseCase: AddAccountUseCase
) : ViewModel() {

    var name by mutableStateOf(DEFAULT_NAME)
    var balance by mutableStateOf(DEFAULT_BALANCE)
    var currency by mutableStateOf(DEFAULT_CURRENCY)

    private fun snapshotAccountBrief() = AccountBrief(
        name,
        balance.toDouble(),
        currency
    )

    fun onAddAccount() {
        viewModelScope.launch {
            addAccountUseCase.invoke(snapshotAccountBrief())
                .collect {}
        }
    }

    companion object {
        private const val DEFAULT_NAME = ""
        private const val DEFAULT_BALANCE = ""
        private const val DEFAULT_CURRENCY = "USD"
    }
}
