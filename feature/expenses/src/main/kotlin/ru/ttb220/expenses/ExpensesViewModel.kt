package ru.ttb220.expenses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.TransactionsRepository
import javax.inject.Inject

class ExpensesViewModel @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
) : ViewModel() {

    private var _expensesScreenState: MutableStateFlow<ExpensesScreenState> =
        MutableStateFlow(ExpensesScreenState.Loading)
    val expensesScreenState = _expensesScreenState.asStateFlow()

    private fun CoroutineScope.getTodayTransactions(): Job = launch {
        accountsRepository.getAllAccounts().collect { accountResult ->
            accountResult.getOrNull()?.let { accounts ->

                val allTransactionResults = accounts.map { account ->
                    transactionsRepository
                        .getAccountTransactionsForPeriod(accountId = account.id)
                }.map { it.first() }

                if (allTransactionResults.any { it.isFailure }) {
                    val firstFailure = allTransactionResults.first { it.isFailure }
                    _expensesScreenState.value =
                        ExpensesScreenState.Error(
                            firstFailure.exceptionOrNull()?.message ?: UNKNOWN_ERROR_MESSAGE
                        )
                }

//                _expensesScreenState.apply {
//                    value = ExpensesScreenState.Loaded(
//                        data = ru.ttb220.presentation.model.screen.ExpensesScreenContent(
//                            expenses =,
//                            totalAmount = allTransactionResults
//                        )
//                    )
//                }
                TODO()
            }
        }
    }

    init {
        viewModelScope.launch{
            val transactions = getTodayTransactions()
        }
    }

    companion object {
        private const val UNKNOWN_ERROR_MESSAGE = "Unknown error"
    }
}