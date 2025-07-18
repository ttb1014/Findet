package ru.ttb220.data.api

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief

/**
 * Each method provides either wrapped data or wrapped domain error.
 */
interface TransactionsRepository {

    fun getTransactionById(id: Int): Flow<SafeResult<Transaction>>

    fun createNewTransaction(transaction: TransactionBrief): Flow<SafeResult<Transaction>>

    fun updateTransactionById(
        id: Int,
        transaction: TransactionBrief
    ): Flow<SafeResult<Transaction>>

    fun deleteTransactionById(id: Int): Flow<SafeResult<Unit>>

    fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<SafeResult<List<Transaction>>>
}