package ru.ttb220.incomes

import android.icu.text.DecimalFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ttb220.domain.GetTodayIncomesForActiveAccountUseCase
import ru.ttb220.model.exception.ForbiddenException
import ru.ttb220.model.exception.IncorrectInputFormatException
import ru.ttb220.model.exception.JsonDecodingException
import ru.ttb220.model.exception.NotFoundException
import ru.ttb220.model.exception.UnauthorizedException
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.presentation.model.IncomeData
import ru.ttb220.presentation.ui.R
import javax.inject.Inject

@HiltViewModel
class IncomesVewModel @Inject constructor(
    private val getTodayIncomesForActiveAccountUseCase: GetTodayIncomesForActiveAccountUseCase,
) : ViewModel() {

    private var _incomesScreenState: MutableStateFlow<IncomesScreenState> =
        MutableStateFlow(IncomesScreenState.Loading)
    val incomesScreenState = _incomesScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            val transactionsFlow = getTodayIncomesForActiveAccountUseCase.invoke()

            try {
                transactionsFlow.collect { transactions ->
                    val totalAmountDouble = transactions.fold(0.0) { acc, transaction ->
                        acc + transaction.amount.toDouble()
                    }
                    val totalAmount = DEFAULT_DECIMAL_FORMAT.format(totalAmountDouble)

                    _incomesScreenState.value = IncomesScreenState.Loaded(
                        data = ru.ttb220.presentation.model.screen.IncomesScreenData(
                            incomes = transactions.map { it.toIncomeState() },
                            totalAmount = totalAmount,
                        )
                    )
                }
            } catch (e: Exception) {
                _incomesScreenState.value = when (e) {
                    is UnauthorizedException, is ForbiddenException -> {
                        IncomesScreenState.ErrorResource(
                            messageId = R.string.error_unauthorized
                        )
                    }

                    is NotFoundException -> {
                        IncomesScreenState.ErrorResource(
                            R.string.error_not_found
                        )
                    }

                    is IncorrectInputFormatException, is JsonDecodingException -> {
                        IncomesScreenState.ErrorResource(
                            R.string.error_bad_data
                        )
                    }

                    else -> e.message?.let {
                        IncomesScreenState.Error(
                            message = it
                        )
                    } ?: IncomesScreenState.ErrorResource(
                        messageId = R.string.error_unknown
                    )
                }
            }
        }
    }

    private fun TransactionDetailed.toIncomeState() = IncomeData(
        title = category.name,
        amount = amount,
    )

    companion object {
        private val DEFAULT_DECIMAL_FORMAT = DecimalFormat("0.00")
    }
}