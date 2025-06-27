package ru.ttb220.account.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.ttb220.data.NetworkMonitor
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.SettingsRepository
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.CurrencyData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.AccountScreenData
import ru.ttb220.presentation.model.util.DomainErrorMessageMapper
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val accountsRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _accountScreenState: MutableStateFlow<AccountScreenState> =
        MutableStateFlow(AccountScreenState.Loading)
    val accountScreenState = _accountScreenState.asStateFlow()

    private fun tryLoadAndUpdateState() = viewModelScope.launch {
        val isOnline = networkMonitor.isOnline.first()
        if (!isOnline) {
            _accountScreenState.value = AccountScreenState.ErrorResource(
                R.string.error_disconnected
            )
            return@launch
        }

        val accountId = settingsRepository.getActiveAccountId().first()

        accountsRepository.getAccountById(accountId).collect { accountDetailedResult ->

            when (accountDetailedResult) {
                is SafeResult.Failure -> {
                    _accountScreenState.value = AccountScreenState.ErrorResource(
                        DomainErrorMessageMapper.toMessageRes(accountDetailedResult.cause)
                    )
                }

                is SafeResult.Success -> {
                    _accountScreenState.value = AccountScreenState.Loaded(
                        data = AccountScreenData(
                            leadingIconId = R.drawable.money_bag,
                            balance = accountDetailedResult.data.balance,
                            currencyData = CurrencyData.RUSSIAN_RUBLE
                        )
                    )
                }
            }
        }
    }

    init {
        tryLoadAndUpdateState()
    }
}
