package ru.ttb220.incomes_history

import android.icu.text.DecimalFormat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.ttb220.data.TimeProvider
import ru.ttb220.domain.GetActiveAccountCurrencyUseCase
import ru.ttb220.domain.GetTransactionsForActiveAccountPeriodUseCase
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.presentation.model.TransactionHistoryData
import ru.ttb220.presentation.model.screen.HistoryScreenData
import ru.ttb220.presentation.model.util.Emoji
import ru.ttb220.presentation.ui.util.EmojiToResourceMapper
import javax.inject.Inject

@HiltViewModel
class IncomesHistoryViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTransactionsForActiveAccountPeriodUseCase: GetTransactionsForActiveAccountPeriodUseCase,
    private val getActiveAccountCurrencyUseCase: GetActiveAccountCurrencyUseCase,
    private val timeProvider: TimeProvider,
) : ViewModel() {

    private val _incomesHistoryScreenState: MutableStateFlow<IncomesHistoryScreenState> =
        MutableStateFlow(IncomesHistoryScreenState.Loading)
    val historyScreenState = _incomesHistoryScreenState.asStateFlow()

    private val activeCurrency = getActiveAccountCurrencyUseCase.invoke()

    private suspend fun populate() = coroutineScope {
        val currencyDeferred = async {
            activeCurrency.first()
        }

        val currencyName = currencyDeferred.await()
        val currency = currencyMap[currencyName] ?: currencyName
        val startDate = timeProvider.startOfAMonth()
        val endDate = timeProvider.today()

        getTransactionsForActiveAccountPeriodUseCase.invoke(
            true,
            startDate,
            endDate,
        ).collect { transactions ->
            val totalAmount = transactions.fold(0.0) { acc, transaction ->
                acc + transaction.amount.toDouble()
            }
            val totalAmountString = DEFAULT_DECIMAL_FORMAT.format(totalAmount) + " $currency"

            _incomesHistoryScreenState.value = IncomesHistoryScreenState.Loaded(
                data = HistoryScreenData(
                    startDate = startDate.toString(),
                    endDate = endDate.toString(),
                    totalAmount = totalAmountString,
                    expenses = transactions.map { it.toPresentation(currency) }
                )
            )
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            populate()
        }
    }

    private fun TransactionDetailed.toPresentation(currency: String): TransactionHistoryData {
        val emojiId = EmojiToResourceMapper[category.emoji]
        val emoji = emojiId?.let {
            Emoji.Resource(it)
        } ?: Emoji.Text(category.emoji)

        val transactionTime = transactionDate
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .run {
                DEFAULT_TIME_FORMAT.format(hour) +
                        ":" +
                        DEFAULT_TIME_FORMAT.format(minute)
            }

        return TransactionHistoryData(
            emoji = emoji,
            name = category.name,
            description = comment,
            amount = "$amount $currency",
            time = transactionTime
        )
    }

    companion object {
        private val DEFAULT_DECIMAL_FORMAT = DecimalFormat("0.00")
        private val DEFAULT_TIME_FORMAT = DecimalFormat("00")

        // TODO: move out to domain
        private val currencyMap = mapOf(
            "RUB" to "₽",
            "USD" to "$"
        )
    }
}