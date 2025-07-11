package ru.ttb220.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.ttb220.account.domain.EditActiveAccountNameUseCase
import ru.ttb220.account.presentation.model.AccountScreenState
import ru.ttb220.data.NetworkMonitor
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.SettingsRepository
import ru.ttb220.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.AccountScreenData
import ru.ttb220.presentation.model.util.CurrencyMapper
import ru.ttb220.presentation.model.util.DomainErrorMessageMapper
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val accountsRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
    private val editActiveAccountNameUseCase: EditActiveAccountNameUseCase,
) : ViewModel() {

    private val _accountScreenState: MutableStateFlow<AccountScreenState> =
        MutableStateFlow(AccountScreenState.Loading)
    val accountScreenState = _accountScreenState.asStateFlow()

    fun tryLoadAndUpdateState() = viewModelScope.launch {
        val isOnline = networkMonitor.isOnline.first()
        if (!isOnline) {
            _accountScreenState.value = AccountScreenState.ErrorResource(
                R.string.error_disconnected
            )
            return@launch
        }

        val accountId = settingsRepository.getActiveAccountId().first()

        val activeAccountCurrencyResult = getActiveAccountCurrencyUseCase().first()

        if(activeAccountCurrencyResult is SafeResult.Failure) {
            _accountScreenState.value = AccountScreenState.ErrorResource(
                DomainErrorMessageMapper.toMessageRes(activeAccountCurrencyResult.cause)
            )
            return@launch
        }

        val activeCurrency = (getActiveAccountCurrencyUseCase().first() as SafeResult.Success).data

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
                            accountName = accountDetailedResult.data.name,
                            leadingIconId = R.drawable.money_bag,
                            balance = accountDetailedResult.data.balance,
                            currencyData = CurrencyMapper.toCurrencyData(activeCurrency),
                        )
                    )
                }
            }
        }
    }

    val accountNameFlow = accountScreenState
        .map { state ->
            when (state) {
                is AccountScreenState.Loaded -> state.data.accountName
                else -> null
            }
        }

    fun updateAccountName(
        newName: String,
        afterEdited: suspend () -> Unit = {},
    ) {
        viewModelScope.launch {
            // result of the change is skipped and nothing is displayed
            editActiveAccountNameUseCase(newName).collect {}
            tryLoadAndUpdateState()
            afterEdited()
        }
    }

    init {
        tryLoadAndUpdateState()
    }
}
