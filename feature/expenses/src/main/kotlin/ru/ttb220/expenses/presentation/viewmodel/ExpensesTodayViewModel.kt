package ru.ttb220.expenses.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.ttb220.data.api.NetworkMonitor
import ru.ttb220.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.expenses.domain.GetTodayExpensesForActiveAccountUseCase
import ru.ttb220.expenses.presentation.model.ExpensesTodayScreenState
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.screen.ExpensesScreenData
import ru.ttb220.presentation.model.toExpenseState
import ru.ttb220.presentation.util.CurrencyMapper
import ru.ttb220.presentation.util.DomainErrorMessageMapper
import ru.ttb220.presentation.util.NumberToStringMapper
import javax.inject.Inject
import javax.inject.Singleton

class ExpensesTodayViewModel @Inject constructor(
    private val networkMonitor: NetworkMonitor,
    private val getTodayExpensesForActiveAccountUseCase: GetTodayExpensesForActiveAccountUseCase,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
) : ViewModel() {

    private var _expensesTodayScreenState: MutableStateFlow<ExpensesTodayScreenState> =
        MutableStateFlow(ExpensesTodayScreenState.Loading)
    val expensesScreenState = _expensesTodayScreenState.asStateFlow()

    private fun tryLoadAndUpdateState() = viewModelScope.launch {
        val isOnline = networkMonitor.isOnline.first()
        if (!isOnline) {
            _expensesTodayScreenState.value = ExpensesTodayScreenState.ErrorResource(
                ru.ttb220.presentation.model.R.string.error_disconnected
            )
            return@launch
        }

        val currencyDeferred = async {
            getActiveAccountCurrencyUseCase.invoke().first()
        }

        getTodayExpensesForActiveAccountUseCase.invoke()
            .collect { transactionsResult ->
                when (transactionsResult) {
                    is SafeResult.Failure -> {
                        _expensesTodayScreenState.value = ExpensesTodayScreenState.ErrorResource(
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
                            _expensesTodayScreenState.value = ExpensesTodayScreenState.ErrorResource(
                                DomainErrorMessageMapper.toMessageRes(currencyCodeResult.cause)
                            )

                            return@collect
                        }

                        currencyCodeResult as SafeResult.Success

                        val currencySymbol =
                            CurrencyMapper.getSymbol(currencyCodeResult.data)

                        val totalAmountString =
                            NumberToStringMapper.map(totalAmountDouble, currencySymbol)

                        _expensesTodayScreenState.value = ExpensesTodayScreenState.Loaded(
                            data = ExpensesScreenData(
                                transactionsResult.data.map { it.toExpenseState(currencySymbol) },
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