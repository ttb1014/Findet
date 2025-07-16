package ru.ttb220.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import ru.ttb220.data.api.SyncableTransactionsRepository
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief
import ru.ttb220.model.transaction.TransactionDetailed
import javax.inject.Inject

class OfflineFirstTransactionsRepository @Inject constructor(

) : SyncableTransactionsRepository {

    override fun createNewTransaction(transaction: TransactionBrief): Flow<SafeResult<Transaction>> {
        TODO("Not yet implemented")
    }

    override fun getTransactionById(id: Int): Flow<SafeResult<TransactionDetailed>> {
        TODO("Not yet implemented")
    }

    override fun updateTransactionById(
        id: Int,
        transaction: TransactionBrief
    ): Flow<SafeResult<TransactionDetailed>> {
        TODO("Not yet implemented")
    }

    override fun deleteTransactionById(id: Int): Flow<SafeResult<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Flow<SafeResult<List<TransactionDetailed>>> {
        TODO("Not yet implemented")
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean {
        TODO("Not yet implemented")
    }
}