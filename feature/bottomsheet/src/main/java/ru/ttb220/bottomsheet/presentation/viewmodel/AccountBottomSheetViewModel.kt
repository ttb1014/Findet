package ru.ttb220.bottomsheet.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.ttb220.bottomsheet.presentation.model.AccountBottomSheetState
import ru.ttb220.bottomsheet.presentation.model.AccountData
import ru.ttb220.data.api.legacy.AccountsRepository
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.presentation.util.CurrencyMapper
import javax.inject.Inject

class AccountBottomSheetViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<AccountBottomSheetState> =
        MutableStateFlow(value = AccountBottomSheetState.Loading)
    val state = _state.asStateFlow()

    private fun populateJob() = viewModelScope.launch {
        val accountsResult = accountsRepository.getAllAccounts().first()

        when (accountsResult) {
            is SafeResult.Failure -> {
                _state.value = AccountBottomSheetState.Error()
            }

            is SafeResult.Success<List<Account>> -> {
                _state.value = AccountBottomSheetState.Loaded(
                    accounts = accountsResult.data.map { it.toPresentation() }
                )
            }
        }
    }

    private fun Account.toPresentation(): AccountData = AccountData(
        accountId = id,
        accountName = name,
        accountBalance = balance,
        accountCurrencySymbol = CurrencyMapper.getSymbol(currency),
    )

    init {
        populateJob()
    }
}
