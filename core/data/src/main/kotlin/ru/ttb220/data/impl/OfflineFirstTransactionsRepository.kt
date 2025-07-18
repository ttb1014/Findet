package ru.ttb220.data.impl

import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import ru.ttb220.data.api.NetworkMonitor
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

// FIXME: Should not block flow until connected and rather add all pending operations to queue.
class OfflineFirstTransactionsRepository @Inject constructor(
    private val timeZone: TimeZone,
    private val transactionsDao: TransactionsDao,
    private val remoteDataSource: RemoteDataSource,
    private val settingsRepository: OfflineFirstSettingsRepository,
    private val networkMonitor: NetworkMonitor,
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

            networkMonitor.isOnline
                .filter { it }
                .first()

            try {
                val remote = flow {
                    emit(
                        remoteDataSource.createNewTransaction(
                            transaction.toTransactionCreateRequestDto()
                        )
                    )
                }.withExpBackoffRetry().first().toTransaction()

                transactionsDao.replaceTransaction(
                    insertedId,
                    remote.toTransactionEntity(synced = true)
                )
            } catch (e: Exception) {
                Log.e("CreateTransaction", "Сервер не доступен, оставляем локальную", e)
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

        networkMonitor.isOnline
            .filter { it }
            .first()

        val remote = flow {
            emit(
                remoteDataSource.updateTransactionById(
                    id = id,
                    transactionUpdateRequestDto = transaction.toTransactionUpdateRequestDto()
                )
            )
        }.withExpBackoffRetry().first()

        remote.toTransaction().let { syncedRemote ->
            transactionsDao.replaceTransaction(
                id.toLong(),
                syncedRemote.toTransactionEntity(synced = true)
            )
        }
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

    private suspend fun overrideWithRemote() = coroutineScope {
        val allAccounts = flow {
            emit(remoteDataSource.getAllAccounts())
        }.withExpBackoffRetry().first()

        val remoteTransactions = allAccounts.map { account ->
            async {
                flow {
                    emit(
                        remoteDataSource.getAccountTransactionsForPeriod(

                            account.id,
                            MIN_DATE,
                            MAX_DATE,
                        )
                    )
                }.withExpBackoffRetry().first()
            }
        }.awaitAll().flatten().map { it.toTransaction() }

        transactionsDao.deleteAll()
        remoteTransactions.forEach { transaction ->
            transactionsDao.insertTransaction(
                transaction.toTransactionEntity(
                    synced = true
                )
            )
        }
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = coroutineScope {
        Log.d(TAG, "syncWith: started")

        overrideWithRemote()

        Log.d(TAG, "syncWith: completed")
        return@coroutineScope true
    }

    companion object {
        private const val TAG = "OfflineFirstTransactionsRepository"
        private val MIN_DATE = LocalDate.parse("0000-01-01")
        private val MAX_DATE = LocalDate.parse("9999-12-31")

    }
}