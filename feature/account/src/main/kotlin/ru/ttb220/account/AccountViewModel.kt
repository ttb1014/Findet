package ru.ttb220.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.SettingsRepository
import ru.ttb220.model.exception.ForbiddenException
import ru.ttb220.model.exception.IncorrectInputFormatException
import ru.ttb220.model.exception.JsonDecodingException
import ru.ttb220.model.exception.NotFoundException
import ru.ttb220.model.exception.UnauthorizedException
import ru.ttb220.presentation.model.util.Currency
import ru.ttb220.presentation.ui.R
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _accountScreenState: MutableStateFlow<AccountScreenState> =
        MutableStateFlow(AccountScreenState.Loading)
    val accountScreenState = _accountScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val accountId = settingsRepository.getActiveAccountId().first()

                accountsRepository.getAccountById(accountId).collect { accountDetailed ->
                    _accountScreenState.value = AccountScreenState.Loaded(
                        data = ru.ttb220.presentation.model.screen.AccountScreenData(
                            leadingIconId = ru.ttb220.presentation.ui.R.drawable.money_bag,
                            balance = accountDetailed.balance,
                            currency = Currency.RUSSIAN_RUBLE
                        )
                    )
                }
            } catch (e: Exception) {
                _accountScreenState.value = when (e) {
                    is UnauthorizedException, is ForbiddenException -> {
                        AccountScreenState.ErrorResource(
                            messageId = R.string.error_unauthorized
                        )
                    }

                    is NotFoundException -> {
                        AccountScreenState.ErrorResource(
                            R.string.error_not_found
                        )
                    }

                    is IncorrectInputFormatException, is JsonDecodingException -> {
                        AccountScreenState.ErrorResource(
                            R.string.error_bad_data
                        )
                    }

                    else -> e.message?.let {
                        AccountScreenState.Error(
                            message = it
                        )
                    } ?: AccountScreenState.ErrorResource(
                        messageId = R.string.error_unknown
                    )
                }
            }
        }
    }
}
