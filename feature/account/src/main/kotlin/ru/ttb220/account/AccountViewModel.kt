package ru.ttb220.account

import android.icu.text.DecimalFormat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.presentation.model.util.Currency
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val accountsRepository: AccountsRepository,
) : ViewModel() {

    private val accountId: Int? = savedStateHandle[ACTIVE_ACCOUNT_ID]

    private val _accountScreenState: MutableStateFlow<AccountScreenState> =
        MutableStateFlow(AccountScreenState.Loading)
    val accountScreenState = _accountScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            if (accountId == null) {
                _accountScreenState.value =
                    AccountScreenState.ErrorResource(ru.ttb220.presentation.ui.R.string.error_unknown)
                return@launch
            }

            accountsRepository.getAccountById(accountId).collect { accountDetailed ->
                _accountScreenState.value = AccountScreenState.Loaded(
                    data = ru.ttb220.presentation.model.screen.AccountScreenData(
                        leadingIconId = ru.ttb220.presentation.ui.R.drawable.money_bag,
                        balance = accountDetailed.balance,
                        currency = Currency.RUSSIAN_RUBLE
                    )
                )
            }
        }
    }
}