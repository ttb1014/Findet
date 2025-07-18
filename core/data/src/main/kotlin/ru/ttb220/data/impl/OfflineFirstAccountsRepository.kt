package ru.ttb220.data.impl

import android.util.Log
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.data.impl.util.withExpBackoffRetry
import ru.ttb220.database.dao.AccountsDao
import ru.ttb220.database.mapper.toAccount
import ru.ttb220.database.mapper.toAccountEntity
import ru.ttb220.model.DomainError
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.network.api.RemoteDataSource
import ru.ttb220.network.api.mapper.toAccount
import ru.ttb220.network.api.mapper.toAccountCreateRequestDto
import javax.inject.Inject

class OfflineFirstAccountsRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val remoteDataSource: RemoteDataSource,
    private val timeProvider: TimeProvider,
    private val settingsRepository: SettingsRepository,
) : AccountsRepository, Syncable {

    override fun getAllAccounts(): Flow<SafeResult<List<Account>>> =
        accountsDao.getAllAccounts().map { accounts ->
            SafeResult.Success(
                data = accounts.map { it.toAccount() }
            )
        }

    override fun createNewAccount(account: AccountBrief): Flow<SafeResult<Account>> = flow {
        val insertedId = accountsDao.insertAccount(
            account.toAccountEntity(
                userId = settingsRepository.getActiveUserId().first(),
                timeStamp = timeProvider.now()
            )
        )
        val localModel = accountsDao.getAccountById(insertedId).first()?.toAccount()
        localModel?.run {
            emit(SafeResult.Success(this))
        }
    }

    override fun getAccountById(id: Int): Flow<SafeResult<Account>> =
        accountsDao.getAccountById(id.toLong()).map { accountEntity ->
            when (accountEntity) {
                null -> SafeResult.Failure(cause = DomainError.NotFound)
                else -> SafeResult.Success(
                    accountEntity.toAccount()
                )
            }
        }

    override fun updateAccountById(id: Int, account: AccountBrief): Flow<SafeResult<Account>> =
        flow local@{
            val updatedId = accountsDao.updateAccount(
                account.toAccountEntity(
                    id = id,
                    userId = settingsRepository.getActiveUserId().first(),
                    timeStamp = timeProvider.now(),
                )
            )

            val localModel = accountsDao.getAccountById(id.toLong()).first()?.toAccount()
            localModel?.run {
                emit(SafeResult.Success(this))
            }
        }

    override fun deleteAccountById(id: Int): Flow<SafeResult<Unit>> = flow {
        val entity = accountsDao.getAccountById(id.toLong()).first()
        if (entity != null)
            accountsDao.deleteAccount(entity)
        emit(SafeResult.Success(Unit))
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = coroutineScope {
        Log.d(TAG, "syncWith: started")

        val localAccounts = accountsDao.getAllAccounts().first()
        val unsyncedAccounts = localAccounts.filterNot { it.synced }
        unsyncedAccounts.map { accountEntity ->
            launch {
                val remoteAccount = flow {
                    emit(
                        remoteDataSource.createNewAccount(
                            accountEntity.toAccount().toAccountCreateRequestDto()
                        )
                    )
                }.withExpBackoffRetry().first().toAccount()
                accountsDao.deleteAccountById(accountEntity.id.toLong())
            }
        }

        val remoteAccounts = flow {
            emit(remoteDataSource.getAllAccounts())
        }.withExpBackoffRetry().first().map { it.toAccount() }

        remoteAccounts.forEach { account ->
            accountsDao.insertAccount(account.toAccountEntity(true))
        }

        Log.d(TAG, "syncWith: completed")
        return@coroutineScope true
    }

    companion object {
        private const val TAG = "AccountsRepo"
    }
}