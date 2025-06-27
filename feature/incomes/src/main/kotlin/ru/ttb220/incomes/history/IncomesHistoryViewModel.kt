package ru.ttb220.incomes.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import ru.ttb220.data.NetworkMonitor
import ru.ttb220.data.TimeProvider
import ru.ttb220.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.domain.GetTransactionsForActiveAccountPeriodUseCase
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.HistoryScreenData
import ru.ttb220.presentation.model.toTransactionHistoryData
import ru.ttb220.presentation.model.util.CurrencySymbolMapper
import ru.ttb220.presentation.model.util.DomainErrorMessageMapper
import ru.ttb220.presentation.model.util.NumberToStringMapper
import javax.inject.Inject

@HiltViewModel
class IncomesHistoryViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val timeZone: TimeZone,
    private val getTransactionsForActiveAccountPeriodUseCase: GetTransactionsForActiveAccountPeriodUseCase,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
    private val timeProvider: TimeProvider,
) : ViewModel() {

    private val _incomesHistoryScreenState: MutableStateFlow<IncomesHistoryScreenState> =
        MutableStateFlow(IncomesHistoryScreenState.Loading)
    val historyScreenState = _incomesHistoryScreenState.asStateFlow()

    private fun tryLoadAndUpdateState() = viewModelScope.launch {
        val isOnline = networkMonitor.isOnline.first()
        if (!isOnline) {
            _incomesHistoryScreenState.value = IncomesHistoryScreenState.ErrorResource(
                R.string.error_disconnected
            )
            return@launch
        }

        val startDate = timeProvider.startOfAMonth().first()
        val endDate = timeProvider.today().first()

        val currencyDeferred = async {
            getActiveAccountCurrencyUseCase.invoke().first()
        }

        getTransactionsForActiveAccountPeriodUseCase.invoke(
            false,
            startDate,
            endDate
        )
            .collect { transactionsResult ->
                when (transactionsResult) {
                    is SafeResult.Failure -> {
                        _incomesHistoryScreenState.value =
                            IncomesHistoryScreenState.ErrorResource(
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
                            _incomesHistoryScreenState.value =
                                IncomesHistoryScreenState.ErrorResource(
                                    DomainErrorMessageMapper.toMessageRes(currencyCodeResult.cause)
                                )

                            return@collect
                        }

                        currencyCodeResult as SafeResult.Success

                        val currencySymbol =
                            CurrencySymbolMapper.getSymbol(currencyCodeResult.data)

                        val totalAmountString =
                            NumberToStringMapper.map(totalAmountDouble, currencySymbol)

                        _incomesHistoryScreenState.value = IncomesHistoryScreenState.Loaded(
                            data = HistoryScreenData(
                                startDate = startDate.toString(),
                                endDate = endDate.toString(),
                                totalAmount = totalAmountString,
                                expenses = transactionsResult.data.map {
                                    it.toTransactionHistoryData(
                                        currencySymbol,
                                        timeZone
                                    )
                                }
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