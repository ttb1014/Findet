package ru.ttb220.data.api.legacy

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief
import ru.ttb220.model.transaction.TransactionDetailed

/**
 * Each method provides either wrapped data or wrapped domain error. Emits single value.
 */
@Deprecated("Use Syncable instead")
interface TransactionsRepository {
    fun createNewTransaction(transaction: TransactionBrief): Flow<SafeResult<Transaction>>

    fun getTransactionById(id: Int): Flow<SafeResult<TransactionDetailed>>

    fun updateTransactionById(
        id: Int,
        transaction: TransactionBrief
    ): Flow<SafeResult<TransactionDetailed>>

    fun deleteTransactionById(id: Int): Flow<SafeResult<Unit>>

    fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
    ): Flow<SafeResult<List<TransactionDetailed>>>
}