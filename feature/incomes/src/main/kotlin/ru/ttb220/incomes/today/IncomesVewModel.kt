package ru.ttb220.incomes.today

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.ttb220.data.NetworkMonitor
import ru.ttb220.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.domain.GetTodayIncomesForActiveAccountUseCase
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.screen.IncomesScreenData
import ru.ttb220.presentation.model.toIncomeData
import ru.ttb220.presentation.model.util.CurrencySymbolMapper
import ru.ttb220.presentation.model.util.DomainErrorMessageMapper
import ru.ttb220.presentation.model.util.NumberToStringMapper
import javax.inject.Inject

@HiltViewModel
class IncomesVewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val getTodayIncomesForActiveAccountUseCase: GetTodayIncomesForActiveAccountUseCase,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
) : ViewModel() {

    private var _incomesScreenState: MutableStateFlow<IncomesScreenState> =
        MutableStateFlow(IncomesScreenState.Loading)
    val incomesScreenState = _incomesScreenState.asStateFlow()

    private fun tryLoadAndUpdateState() = viewModelScope.launch {
        val isOnline = networkMonitor.isOnline.first()
        if (!isOnline) {
            _incomesScreenState.value = IncomesScreenState.ErrorResource(
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
                        _incomesScreenState.value = IncomesScreenState.ErrorResource(
                            DomainErrorMessageMapper.toMessageRes(transactionsResult.cause)
                        )
                    }

                    is SafeResult.Success -> {
                        val totalAmountDouble =
                            transactionsResult.data.fold(0.0) { acc, transaction ->
                                acc + transaction.amount.toDouble()
                            }
                        val currencyCodeResult = currencyDeferred.await()

                        // if we get an error while collecting currency code -> show error and return
                        if (currencyCodeResult is SafeResult.Failure) {
                            _incomesScreenState.value = IncomesScreenState.ErrorResource(
                                DomainErrorMessageMapper.toMessageRes(currencyCodeResult.cause)
                            )

                            return@collect
                        }

                        currencyCodeResult as SafeResult.Success

                        val currencySymbol =
                            CurrencySymbolMapper.getSymbol(currencyCodeResult.data)

                        val totalAmountString =
                            NumberToStringMapper.map(totalAmountDouble, currencySymbol)

                        _incomesScreenState.value = IncomesScreenState.Loaded(
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