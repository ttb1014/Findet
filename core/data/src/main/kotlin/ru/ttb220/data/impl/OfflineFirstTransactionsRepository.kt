package ru.ttb220.data.impl

import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.data.impl.util.withExpBackoffRetry
import ru.ttb220.database.dao.TransactionsDao
import ru.ttb220.database.mapper.toTransaction
import ru.ttb220.database.mapper.toTransactionEntity
import ru.ttb220.model.DomainError
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief
import ru.ttb220.network.api.RemoteDataSource
import ru.ttb220.network.api.mapper.toTransaction
import ru.ttb220.network.api.mapper.toTransactionCreateRequestDto
import ru.ttb220.network.api.mapper.toTransactionUpdateRequestDto
import javax.inject.Inject

class OfflineFirstTransactionsRepository @Inject constructor(
    private val timeZone: TimeZone,
    private val transactionsDao: TransactionsDao,
    private val remoteDataSource: RemoteDataSource,
    private val settingsRepository: OfflineFirstSettingsRepository,
    private val timeProvider: TimeProvider
) : TransactionsRepository, Syncable {

    override fun createNewTransaction(transaction: TransactionBrief): Flow<SafeResult<Transaction>> =
        flow {
            val insertedId = transactionsDao.insertTransaction(
                transaction.toTransactionEntity(
                    createdAt = timeProvider.now(), updatedAt = timeProvider.now()
                )
            )
            val localModel = transactionsDao.getTransactionById(insertedId).first()?.toTransaction()
            localModel?.let {
                emit(SafeResult.Success(it))
            }
        }

    override fun getTransactionById(id: Int): Flow<SafeResult<Transaction>> =
        transactionsDao.getTransactionById(id.toLong()).map { transactionEntity ->
            when (transactionEntity) {
                null -> SafeResult.Failure(DomainError.NotFound)
                else -> SafeResult.Success(transactionEntity.toTransaction())
            }
        }

    override fun updateTransactionById(
        id: Int, transaction: TransactionBrief
    ): Flow<SafeResult<Transaction>> = flow local@{
        val oldModel = transactionsDao.getTransactionById(id.toLong()).first()

        if (oldModel == null) {
            emit(SafeResult.Failure(DomainError.NotFound))
            return@local
        }

        val updatedLocalModel = transactionsDao.updateTransaction(
            transaction.toTransactionEntity(
                id = id, createdAt = oldModel.createdAt, updatedAt = timeProvider.now()
            )
        )
    }

    override fun deleteTransactionById(id: Int): Flow<SafeResult<Unit>> = flow {
        val entity = transactionsDao.getTransactionById(id.toLong()).first()
        if (entity != null) transactionsDao.deleteTransaction(entity)
    }

    override fun getAccountTransactionsForPeriod(
        accountId: Int, startDate: LocalDate, endDate: LocalDate
    ): Flow<SafeResult<List<Transaction>>> = transactionsDao.getAllTransactions(
        accountId.toLong(),
        startDate.atStartOfDayIn(timeZone),
        endDate.atStartOfDayIn(timeZone)
    ).map { transactions ->
        SafeResult.Success(transactions.map {
            it.toTransaction()
        })
    }


    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = coroutineScope {
        Log.d(TAG, "syncWith: started")

        val allAccounts = remoteDataSource.getAllAccounts()
        val ourAccountIds = allAccounts.map { it.id }.toSet()

        val remoteTransactions = allAccounts.flatMap { account ->
            remoteDataSource.getAccountTransactionsForPeriod(
                accountId = account.id,
                startDate = MIN_DATE,
                endDate = MAX_DATE
            )
        }.map { it.toTransaction() }
        val remoteMap = remoteTransactions.associateBy { it.id }

        val localTransactions = transactionsDao.getAllTransactions().first()
        val unsyncedLocal = localTransactions
            .filterNot { it.synced }
            .map { it.toTransaction() }
        val localUnsyncedIds = unsyncedLocal.map { it.id }.toSet()
        val syncedLocal = localTransactions
            .filter { it.synced }
            .map { it.toTransaction() }
        val localSyncedIds = syncedLocal.map { it.id }.toSet()

        val toInsertRemote = mutableListOf<Transaction>()
        val toUpdateRemote = mutableListOf<Transaction>()
        val toDeleteRemote = mutableListOf<Transaction>()
        val toInsertLocal = mutableListOf<Transaction>()
        val toUpdateLocal = mutableListOf<Transaction>()
        val toDeleteLocal = mutableListOf<Transaction>()

        for (local in unsyncedLocal) {
            val remote = remoteMap[local.id]
            if (remote == null) {
                toInsertRemote.add(local)
                continue
            }
            if (remote.accountId !in ourAccountIds) {
                toInsertRemote.add(local)
                continue
            }
            if (local.updatedAt > remote.updatedAt) {
                toUpdateRemote.add(local)
                continue
            }
        }

        val insertRemoteJob = toInsertRemote.map { transaction ->
            launch {
                flow {
                    emit(remoteDataSource.createNewTransaction(transaction.toTransactionCreateRequestDto()))
                }.withExpBackoffRetry().first()
            }
        }
        val updateRemoteJob = toUpdateRemote.map { transaction ->
            launch {
                flow {
                    emit(
                        remoteDataSource.updateTransactionById(
                            transaction.id,
                            transaction.toTransactionUpdateRequestDto()
                        )
                    )
                }.withExpBackoffRetry().first()
            }
        }
        insertRemoteJob.joinAll()
        updateRemoteJob.joinAll()

        val remoteTransactionsSynced = allAccounts.flatMap { account ->
            remoteDataSource.getAccountTransactionsForPeriod(
                accountId = account.id,
                startDate = MIN_DATE,
                endDate = MAX_DATE
            )
        }.map { it.toTransaction() }
        transactionsDao.deleteAll()
        remoteTransactions.map { transaction ->
            launch {
                transactionsDao.insertTransaction(transaction.toTransactionEntity(synced = true))
            }
        }.joinAll()

        Log.d(TAG, "syncWith: completed")
        return@coroutineScope true
    }


    companion object {
        private const val TAG = "OfflineFirstTransactionsRepository"
        private val MIN_DATE = LocalDate.parse("0000-01-01")
        private val MAX_DATE = LocalDate.parse("9999-12-31")

    }
}