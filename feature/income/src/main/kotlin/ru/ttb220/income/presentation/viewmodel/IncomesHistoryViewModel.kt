package ru.ttb220.income.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.data.api.NetworkMonitor
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.income.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.income.domain.GetTransactionsForActiveAccountPeriodUseCase
import ru.ttb220.income.presentation.model.IncomesHistoryScreenState
import ru.ttb220.model.SafeResult
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.HistoryScreenData
import ru.ttb220.presentation.model.toTransactionHistoryData
import ru.ttb220.presentation.model.util.CurrencyMapper
import ru.ttb220.presentation.model.util.DomainErrorMessageMapper
import ru.ttb220.presentation.model.util.NumberToStringMapper
import javax.inject.Inject

class IncomesHistoryViewModel @Inject constructor(
    private val timeZone: TimeZone,
    private val getTransactionsForActiveAccountPeriodUseCase: GetTransactionsForActiveAccountPeriodUseCase,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
    private val timeProvider: TimeProvider,
) : ViewModel() {

    private val _incomesHistoryScreenState: MutableStateFlow<IncomesHistoryScreenState> =
        MutableStateFlow(IncomesHistoryScreenState.Loading)
    val historyScreenState = _incomesHistoryScreenState.asStateFlow()

    private var startDateMillis: Long = 0L
    private var endDateMillis: Long = 0L

    private fun initializeDates() = viewModelScope.launch {
        val startOfMonth = timeProvider.startOfAMonth()
        val today = timeProvider.today()

        startDateMillis = startOfMonth.asEpochMillisAtTimeZone(timeZone)
        endDateMillis = today.asEpochMillisAtTimeZone(timeZone)
    }

    private fun parseDatesAndLoadData() = viewModelScope.launch {
        val startDate = startDateMillis.asLocalDateAtTimeZone(timeZone)
        val endDate = endDateMillis.asLocalDateAtTimeZone(timeZone)

        loadDataAndUpdateState(startDate, endDate)
    }

    private fun tryLoadAndUpdateState() = viewModelScope.launch {
        val startDate =
            Instant.fromEpochMilliseconds(startDateMillis).toLocalDateTime(timeZone).date
        val endDate = Instant.fromEpochMilliseconds(endDateMillis).toLocalDateTime(timeZone).date

        loadDataAndUpdateState(startDate, endDate)
    }

    private suspend fun loadDataAndUpdateState(startDate: LocalDate, endDate: LocalDate) {
        val currencyDeferred = viewModelScope.async {
            getActiveAccountCurrencyUseCase.invoke().first()
        }

        getTransactionsForActiveAccountPeriodUseCase.invoke(
            true,
            startDate,
            endDate
        ).collect { transactionsResult ->
            when (transactionsResult) {
                is SafeResult.Failure -> {
                    _incomesHistoryScreenState.value =
                        IncomesHistoryScreenState.ErrorResource(
                            DomainErrorMessageMapper.toMessageRes(transactionsResult.cause)
                        )
                }

                is SafeResult.Success -> {
                    val totalAmountDouble =
                        transactionsResult.data.fold(0.0) { acc, transaction ->
                            acc + transaction.amount.toDouble()
                        }
                    val currencyCodeResult = currencyDeferred.await()

                    if (currencyCodeResult is SafeResult.Failure) {
                        _incomesHistoryScreenState.value =
                            IncomesHistoryScreenState.ErrorResource(
                                DomainErrorMessageMapper.toMessageRes(currencyCodeResult.cause)
                            )
                        return@collect
                    }

                    currencyCodeResult as SafeResult.Success

                    val currencySymbol =
                        CurrencyMapper.getSymbol(currencyCodeResult.data)

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

    fun onEndDateSelected(dateMillis: Long?) {
        if (dateMillis == null) return

        endDateMillis = dateMillis

        parseDatesAndLoadData()
    }

    fun onStartDateSelected(dateMillis: Long?) {
        if(dateMillis == null) return

        startDateMillis = dateMillis

        parseDatesAndLoadData()
    }

    private fun Long.asLocalDateAtTimeZone(timeZone: TimeZone): LocalDate {
        return Instant.fromEpochMilliseconds(this).toLocalDateTime(timeZone).date
    }

    private fun LocalDate.asEpochMillisAtTimeZone(timeZone: TimeZone): Long =
        this.atStartOfDayIn(timeZone).toEpochMilliseconds()

    init {
        viewModelScope.launch {
            initializeDates().join()

            parseDatesAndLoadData()
        }
    }
}