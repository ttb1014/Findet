package ru.ttb220.expenses

import android.icu.text.DecimalFormat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ttb220.domain.GetTodayExpensesForActiveAccountUseCase
import ru.ttb220.model.exception.ForbiddenException
import ru.ttb220.model.exception.IncorrectInputFormatException
import ru.ttb220.model.exception.JsonDecodingException
import ru.ttb220.model.exception.NotFoundException
import ru.ttb220.model.exception.UnauthorizedException
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.presentation.model.ExpenseData
import ru.ttb220.presentation.model.screen.ExpensesScreenData
import ru.ttb220.presentation.model.util.Emoji
import ru.ttb220.presentation.ui.R
import ru.ttb220.presentation.ui.util.EmojiToResourceMapper
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val getTodayExpensesForActiveAccountUseCase: GetTodayExpensesForActiveAccountUseCase,
) : ViewModel() {

    private var _expensesScreenState: MutableStateFlow<ExpensesScreenState> =
        MutableStateFlow(ExpensesScreenState.Loading)
    val expensesScreenState = _expensesScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            val transactionsFlow = getTodayExpensesForActiveAccountUseCase.invoke()

            try {
                transactionsFlow.collect { transactions ->
                    val totalAmountDouble = transactions.fold(0.0) { acc, transaction ->
                        acc + transaction.amount.toDouble()
                    }
                    val totalAmount = DEFAULT_DECIMAL_FORMAT.format(totalAmountDouble)

                    _expensesScreenState.value = ExpensesScreenState.Loaded(
                        data = ExpensesScreenData(
                            expenses = transactions.map { it.toExpenseState() },
                            totalAmount = totalAmount,
                        )
                    )
                }
            } catch (e: Exception) {
                _expensesScreenState.value = when (e) {
                    is UnauthorizedException, is ForbiddenException -> {
                        ExpensesScreenState.ErrorResource(
                            messageId = R.string.error_unauthorized
                        )
                    }

                    is NotFoundException -> {
                        ExpensesScreenState.ErrorResource(
                            R.string.error_not_found
                        )
                    }

                    is IncorrectInputFormatException, is JsonDecodingException -> {
                        ExpensesScreenState.ErrorResource(
                            R.string.error_bad_data
                        )
                    }

                    else -> e.message?.let {
                        ExpensesScreenState.Error(
                            message = it
                        )
                    } ?: ExpensesScreenState.ErrorResource(
                        messageId = R.string.error_unknown
                    )
                }
            }
        }
    }

    private fun TransactionDetailed.toExpenseState() = ExpenseData(
        emojiId = category.emoji.let emoji@{ emojiString ->
            val emojiRes = EmojiToResourceMapper[emojiString]
            emojiRes?.let { res ->
                return@emoji Emoji.Resource(res)
            }

            Emoji.Text(emojiString)
        },
        name = category.name,
        shortDescription = comment,
        amount = amount,
    )

    companion object {
        private val DEFAULT_DECIMAL_FORMAT = DecimalFormat("0.00")
    }
}