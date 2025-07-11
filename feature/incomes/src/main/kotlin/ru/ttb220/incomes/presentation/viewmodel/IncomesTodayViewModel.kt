package ru.ttb220.incomes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.ttb220.data.api.NetworkMonitor
import ru.ttb220.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.incomes.domain.GetTodayIncomesForActiveAccountUseCase
import ru.ttb220.incomes.presentation.model.IncomesTodayScreenState
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.screen.IncomesScreenData
import ru.ttb220.presentation.model.toIncomeData
import ru.ttb220.presentation.util.CurrencyMapper
import ru.ttb220.presentation.util.DomainErrorMessageMapper
import ru.ttb220.presentation.util.NumberToStringMapper
import javax.inject.Inject

class IncomesTodayViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val getTodayIncomesForActiveAccountUseCase: GetTodayIncomesForActiveAccountUseCase,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
) : ViewModel() {

    private var _incomesTodayScreenState: MutableStateFlow<IncomesTodayScreenState> =
        MutableStateFlow(IncomesTodayScreenState.Loading)
    val incomesScreenState = _incomesTodayScreenState.asStateFlow()

    private fun tryLoadAndUpdateState() = viewModelScope.launch {
        val isOnline = networkMonitor.isOnline.first()
        if (!isOnline) {
            _incomesTodayScreenState.value = IncomesTodayScreenState.ErrorResource(
                ru.ttb220.presentation.model.R.string.error_disconnected
            )
            return@launch
        }

        val currencyDeferred = async {
            getActiveAccountCurrencyUseCase.invoke().first()
        }

        getTodayIncomesForActiveAccountUseCase.invoke()
            .collect { transactionsResult ->
                when (transactionsResult) {
                    is SafeResult.Failure -> {
                        _incomesTodayScreenState.value = IncomesTodayScreenState.ErrorResource(
                            DomainErrorMessageMapper.toMessageRes(transactionsResult.cause)
                        )
                    }

                    // calculate total amount -> convert it to string using mapper from presentation/model/mapper ->
                    // update ui state
                    is SafeResult.Success -> {
                        val totalAmountDouble =
                            transactionsResult.data.fold(0.0) { acc, transaction ->
                                acc + transaction.amount.toDouble()
                            }
                        val currencyCodeResult = currencyDeferred.await()

                        // if we get an error while collecting currency code -> show error and return
                        if (currencyCodeResult is SafeResult.Failure) {
                            _incomesTodayScreenState.value = IncomesTodayScreenState.ErrorResource(
                                DomainErrorMessageMapper.toMessageRes(currencyCodeResult.cause)
                            )

                            return@collect
                        }

                        currencyCodeResult as SafeResult.Success

                        val currencySymbol =
                            CurrencyMapper.getSymbol(currencyCodeResult.data)

                        val totalAmountString =
                            NumberToStringMapper.map(totalAmountDouble, currencySymbol)

                        _incomesTodayScreenState.value = IncomesTodayScreenState.Loaded(
                            data = IncomesScreenData(
                                transactionsResult.data.map { it.toIncomeData(currencySymbol) },
                                totalAmount = totalAmountString
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