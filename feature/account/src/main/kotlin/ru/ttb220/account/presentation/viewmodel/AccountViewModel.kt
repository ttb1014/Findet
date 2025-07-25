package ru.ttb220.account.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.ttb220.account.domain.EditActiveAccountNameUseCase
import ru.ttb220.account.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.account.domain.GetMonthTransactionsUseCase
import ru.ttb220.account.domain.MakeBarChartDataUseCase
import ru.ttb220.account.presentation.model.AccountScreenData
import ru.ttb220.account.presentation.model.ChartType
import ru.ttb220.account.presentation.state.AccountScreenState
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.util.CurrencyMapper
import ru.ttb220.presentation.model.util.DomainErrorMessageMapper
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository,
    private val transactionsRepository: SettingsRepository,
    private val timeProvider: TimeProvider,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
    private val editActiveAccountNameUseCase: EditActiveAccountNameUseCase,
    private val makeBarChartDataUseCase: MakeBarChartDataUseCase,
    private val getMonthTransactionsUseCase: GetMonthTransactionsUseCase,
) : ViewModel() {

    private val _accountScreenState: MutableStateFlow<AccountScreenState> =
        MutableStateFlow(AccountScreenState.Loading)
    val accountScreenState = _accountScreenState.asStateFlow()

    // TODO Remove. Unused in UI
    private val _isAxisShown = MutableStateFlow(false)
    val isAxisShown = _isAxisShown.asStateFlow()

    private val _chartType = MutableStateFlow(ChartType.BAR)
    val chartType = _chartType.asStateFlow()


    /**
     * should only be called when state is loaded
     */
    fun setIsAxisShown(isShown: Boolean) {
        _isAxisShown.value = isShown

        // we assume state is loaded
        // TODO: inspect
        val currentAccountData = (_accountScreenState.value as AccountScreenState.Loaded).data
        val barChartData = currentAccountData.barChartData

        _accountScreenState.value = AccountScreenState.Loaded(
            currentAccountData.copy(
                barChartData = barChartData.copy(isAxisShown = isShown)
            )
        )
    }

    fun onChartTypeChange(chartType: ChartType) {
        _chartType.value = chartType

        // we assume state is loaded
        // TODO: inspect
        val currentAccountData = (_accountScreenState.value as AccountScreenState.Loaded).data

        _accountScreenState.value = AccountScreenState.Loaded(
            currentAccountData.copy(
                chartType = chartType
            )
        )
    }

    fun tryLoadAndUpdateState() = viewModelScope.launch {
        val accountId = settingsRepository.getActiveAccountId().first()

        val activeAccountCurrencyResult = getActiveAccountCurrencyUseCase().first()

        if (activeAccountCurrencyResult is SafeResult.Failure) {
            _accountScreenState.value = AccountScreenState.ErrorResource(
                DomainErrorMessageMapper.toMessageRes(activeAccountCurrencyResult.cause)
            )
            return@launch
        }

        val transactions = async {
            getMonthTransactionsUseCase()
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
                            balance = accountDetailedResult.data.balance.toString(),
                            currencyData = CurrencyMapper.toCurrencyData(activeCurrency),
                            barChartData = makeBarChartDataUseCase(
                                transactions = transactions.await(),
                                startPeriod = timeProvider.dayMonthAgo(),
                                endPeriod = timeProvider.today(),
                                isAxisShown = isAxisShown.value,
                            ),
                            chartType = chartType.value,
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
