package ru.ttb220.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.TransactionsRepository
import ru.ttb220.domain.GetTransactionsForAllAccountsUseCase
import ru.ttb220.model.exception.IncorrectInputFormatException
import ru.ttb220.model.exception.ServerErrorException
import ru.ttb220.model.exception.UnauthorizedException
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.presentation.model.ExpenseState
import ru.ttb220.presentation.model.screen.ExpensesScreenContent
import ru.ttb220.presentation.model.util.Emoji
import ru.ttb220.presentation.ui.util.EmojiToResourceMapper
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
    private val getTransactionsForAllAccountsUseCase: GetTransactionsForAllAccountsUseCase,
) : ViewModel() {

    private var _expensesScreenState: MutableStateFlow<ExpensesScreenState> =
        MutableStateFlow(ExpensesScreenState.Loading)
    val expensesScreenState = _expensesScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            val transactionsFlow = getTransactionsForAllAccountsUseCase.invoke(false)

            try {
                transactionsFlow.collect { transactions ->
                    val totalAmount = transactions.fold(0) { acc, transaction ->
                        acc + transaction.amount.toInt()
                    }.toString()

                    _expensesScreenState.value = ExpensesScreenState.Loaded(
                        data = ExpensesScreenContent(
                            expenses = transactions.map { it.toExpenseState() },
                            totalAmount = totalAmount,
                        )
                    )
                }
            } catch (e: UnauthorizedException) {
                TODO()
            } catch (e: IncorrectInputFormatException) {
                TODO()
            } catch (e: ServerErrorException) {
                TODO()
            } catch (e: Exception) {
                TODO()
            }
        }
    }

    private fun TransactionDetailed.toExpenseState() = ExpenseState(
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
        private const val UNKNOWN_ERROR_MESSAGE = "Unknown error"
    }
}