package ru.ttb220.data.impl

import android.util.Log
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.ttb220.data.api.SyncableAccountsRepository
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.data.impl.legacy.toAccountCreateRequestDto
import ru.ttb220.database.dao.AccountsDao
import ru.ttb220.database.model.entity.toAccount
import ru.ttb220.database.model.entity.toAccountDetailed
import ru.ttb220.database.model.entity.toAccountEntity
import ru.ttb220.model.DomainError
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.model.account.AccountDetailed
import ru.ttb220.model.account.AccountHistory
import ru.ttb220.network.api.RemoteDataSource
import ru.ttb220.network.api.exception.ApiException
import ru.ttb220.network.api.exception.JsonDecodingException
import ru.ttb220.network.api.model.request.toAccountRequestDto
import ru.ttb220.network.api.model.response.toAccount
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class OfflineFirstAccountsRepository @Inject constructor(
    private val accountsDao: AccountsDao,
    private val remoteDataSource: RemoteDataSource,
) : SyncableAccountsRepository {

    override fun getAllAccounts(): Flow<SafeResult<List<Account>>> =
        accountsDao.getAllAccounts().map { accounts ->
            SafeResult.Success(
                accounts.map {
                    it.toAccount()
                })
        }

    override fun createNewAccount(account: AccountBrief): Flow<SafeResult<Account>> = flow {
        val accountResponse = remoteDataSource.createNewAccount(account.toAccountCreateRequestDto())
        accountsDao.insertAccount(accountResponse.toAccount().toAccountEntity())
    }

    override fun getAccountById(id: Int): Flow<SafeResult<AccountDetailed>> =
        accountsDao.getAccountById(id.toLong()).map { accountEntity ->
            when (accountEntity) {
                null -> SafeResult.Failure(cause = DomainError.NotFound)
                else -> SafeResult.Success(
                    accountEntity.toAccountDetailed(
                        emptyList(),
                        emptyList()
                    )
                )
            }
        }

    override fun updateAccountById(
        id: Int,
        account: AccountBrief
    ): Flow<SafeResult<Account>> = flow {
        var accountEntity = accountsDao.getAccountById(id.toLong()).first()

        if (accountEntity == null) SafeResult.Failure(cause = DomainError.NotFound)

        accountEntity = accountEntity!!.copy(
            name = account.name,
            currency = account.currency,
            balance = account.balance.toDouble()
        )
        accountsDao.updateAccount(accountEntity)
        emit(SafeResult.Success(accountEntity.toAccount()))
    }

    override fun deleteAccountById(id: Int): Flow<SafeResult<Unit>> {
        TODO("Not yet implemented")
    }

    override fun getAccountHistoryById(id: Int): Flow<SafeResult<AccountHistory>> {
        TODO("Not yet implemented")
    }

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = coroutineScope {
        Log.d(this@OfflineFirstAccountsRepository::class.simpleName, "syncWith: started")

        val localAsync = async {
            accountsDao.getAllAccounts().first().map { it.toAccount() }
        }
        val remoteAsync = async {
            remoteDataSource.getAllAccounts().map { it.toAccount() }
        }
        val local = localAsync.await()
        val remote = remoteAsync.await()

        val localMap = local.associateBy { it.id }
        val remoteMap = remote.associateBy { it.id }

        val toInsertLocal = mutableListOf<Account>()
        val toDeleteLocal = mutableListOf<Account>()
        val toUpdateLocal = mutableListOf<Account>()

        val toUpdateRemote = mutableListOf<Account>()

        for ((id, remoteAcc) in remoteMap) {
            val localAcc = localMap[id]
            when {
                localAcc == null -> {
                    toInsertLocal.add(remoteAcc)
                }

                remoteAcc.updatedAt > localAcc.updatedAt -> {
                    toUpdateLocal.add(remoteAcc)
                }

                remoteAcc.updatedAt < localAcc.updatedAt -> {
                    toUpdateRemote.add(localAcc)
                }
            }
        }

        for ((id, localAcc) in localMap) {
            if (id !in remoteMap) {
                toDeleteLocal.add(localAcc)
            }
        }

        val insertLocalRes = async {
            accountsDao.insertAccounts(toInsertLocal.map { it.toAccountEntity() })
        }
        val updateLocalRes = async {
            accountsDao.updateAccounts(toUpdateLocal.map { it.toAccountEntity() })
        }
        val deleteLocalRes = async {
            accountsDao.deleteAccounts(toDeleteLocal.map { it.toAccountEntity() })
        }

        val updateRemoteRes = toUpdateRemote.map { account ->
            async {
                try {
                    remoteDataSource.updateAccountById(
                        id = account.id,
                        accountCreateRequestDto = account.toAccountRequestDto()
                    )
                    true
                } catch (e: CancellationException) {
                    throw e
                } catch (_: ApiException) {
                    false
                } catch (_: JsonDecodingException) {
                    false
                }
            }
        }.awaitAll().all { it }

        insertLocalRes.await()
        updateLocalRes.await()
        deleteLocalRes.await()

        Log.d(TAG, "syncWith: started")

        true
    }

    companion object {
        private const val TAG = "OfflineFirstAccountsRepository"
    }
}