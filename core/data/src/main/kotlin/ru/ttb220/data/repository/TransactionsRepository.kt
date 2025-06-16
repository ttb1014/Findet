package ru.ttb220.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief
import ru.ttb220.model.transaction.TransactionDetailed

interface TransactionsRepository {
    fun createNewTransaction(transaction: TransactionBrief): Flow<Result<Transaction>>

    fun getTransactionById(id: Int): Flow<Result<TransactionDetailed>>

    fun updateTransactionById(
        id: Int,
        transaction: TransactionBrief
    ): Flow<Result<TransactionDetailed>>

    fun deleteTransactionById(id: Int): Flow<Result<Unit>>

    fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: Instant? = null,
        endDate: Instant? = null,
    ): Flow<Result<List<TransactionDetailed>>>
}