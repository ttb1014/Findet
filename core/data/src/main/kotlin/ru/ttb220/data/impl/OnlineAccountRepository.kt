package ru.ttb220.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.impl.util.withRetry
import ru.ttb220.data.impl.util.wrapToSafeResult
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.model.account.AccountDetailed
import ru.ttb220.model.account.AccountHistory
import ru.ttb220.network.api.RemoteDataSource
import ru.ttb220.network.api.model.request.AccountCreateRequestDto
import ru.ttb220.network.api.model.response.toAccount
import ru.ttb220.network.api.model.response.toAccountDetailed
import ru.ttb220.network.api.model.response.toAccountHistory
import javax.inject.Inject

class OnlineAccountRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : AccountsRepository {

    override fun getAllAccounts(): Flow<SafeResult<List<Account>>> = flow<List<Account>> {
        emit(remoteDataSource.getAllAccounts().map { it.toAccount() })
    }.withRetry()
        .wrapToSafeResult()

    override fun createNewAccount(account: AccountBrief): Flow<SafeResult<Account>> =
        flow<Account> {
            emit(remoteDataSource.createNewAccount(account.toAccountCreateRequestDto()).toAccount())
        }.withRetry()
            .wrapToSafeResult()

    override fun getAccountById(id: Int): Flow<SafeResult<AccountDetailed>> =
        flow<AccountDetailed> {
            emit(remoteDataSource.getAccountById(id).toAccountDetailed())
        }.withRetry()
            .wrapToSafeResult()

    override fun updateAccountById(id: Int, account: AccountBrief): Flow<SafeResult<Account>> =
        flow<Account> {
            emit(
                remoteDataSource.updateAccountById(id, account.toAccountCreateRequestDto()).toAccount()
            )
        }.withRetry()
            .wrapToSafeResult()

    override fun deleteAccountById(id: Int): Flow<SafeResult<Unit>> = flow {
        emit(remoteDataSource.deleteAccountById(id))
    }.withRetry()
        .wrapToSafeResult()

    override fun getAccountHistoryById(id: Int): Flow<SafeResult<AccountHistory>> =
        flow<AccountHistory> {
            emit(remoteDataSource.getAccountHistoryById(id).toAccountHistory())
        }.withRetry().wrapToSafeResult()
}

fun AccountBrief.toAccountCreateRequestDto() = AccountCreateRequestDto(
    name = name, balance = balance, currency = currency
)