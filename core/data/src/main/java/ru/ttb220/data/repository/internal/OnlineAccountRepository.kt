package ru.ttb220.data.repository.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.util.withRetry
import ru.ttb220.data.util.wrapToResult
import ru.ttb220.domain_model.account.Account
import ru.ttb220.domain_model.account.AccountBrief
import ru.ttb220.domain_model.account.AccountChangeType
import ru.ttb220.domain_model.account.AccountDetailed
import ru.ttb220.domain_model.account.AccountHistory
import ru.ttb220.domain_model.account.AccountHistoryEntry
import ru.ttb220.domain_model.account.AccountState
import ru.ttb220.domain_model.transaction.TransactionStat
import ru.ttb220.network.RemoteDataSource
import ru.ttb220.network.model.AccountHistoryEntryDto
import ru.ttb220.network.model.AccountStateDto
import ru.ttb220.network.model.ChangeTypeDto
import ru.ttb220.network.model.StatItemDto
import ru.ttb220.network.model.request.AccountCreateRequest
import ru.ttb220.network.model.response.AccountDetailedResponse
import ru.ttb220.network.model.response.AccountHistoryResponse
import ru.ttb220.network.model.response.AccountResponse
import javax.inject.Inject

internal class OnlineAccountRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : AccountsRepository {

    override fun getAllAccounts(): Flow<Result<List<Account>>> = flow<List<Account>> {
        emit(remoteDataSource.getAllAccounts().map { it.toAccount() })
    }.withRetry()
        .wrapToResult()

    override fun createNewAccount(account: AccountBrief): Flow<Result<Account>> =
        flow<Account> {
            emit(remoteDataSource.createNewAccount(account.toAccountCreateRequest()).toAccount())
        }.withRetry()
            .wrapToResult()

    override fun getAccountById(id: Int): Flow<Result<AccountDetailed>> =
        flow<AccountDetailed> {
            emit(remoteDataSource.getAccountById(id).toAccountDetailed())
        }.withRetry()
            .wrapToResult()

    override fun updateAccountById(id: Int, account: AccountBrief): Flow<Result<Account>> =
        flow<Account> {
            emit(
                remoteDataSource.updateAccountById(id, account.toAccountCreateRequest()).toAccount()
            )
        }.withRetry()
            .wrapToResult()

    override fun deleteAccountById(id: Int): Flow<Result<Unit>> = flow {
        emit(remoteDataSource.deleteAccountById(id))
    }.withRetry()
        .wrapToResult()

    override fun getAccountHistoryById(id: Int): Flow<Result<AccountHistory>> =
        flow<AccountHistory> {
            emit(remoteDataSource.getAccountHistoryById(id).toAccountHistory())
        }.withRetry()
            .wrapToResult()
}

// TODO: move mappings to original class files
private fun AccountHistoryResponse.toAccountHistory(): AccountHistory =
    AccountHistory(accountId = accountId,
        accountName = accountName,
        currency = currency,
        currentBalance = currentBalance,
        history = history.map { it.toAccountHistoryEntry() })

private fun AccountHistoryEntryDto.toAccountHistoryEntry() = AccountHistoryEntry(
    id = id,
    accountId = accountId,
    changeType = changeTypeDto.toAccountChangeType(),
    previousState = previousState?.toAccountState(),
    newState = newState.toAccountState(),
    changeTimestamp = changeTimeStamp,
    createdAt = createdAt
)

private fun AccountStateDto.toAccountState(): AccountState = AccountState(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
)

private fun ChangeTypeDto.toAccountChangeType(): AccountChangeType = when (this) {
    ChangeTypeDto.CREATION -> AccountChangeType.CREATION
    ChangeTypeDto.MODIFICATION -> AccountChangeType.MODIFICATION
}

private fun AccountDetailedResponse.toAccountDetailed(): AccountDetailed = AccountDetailed(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
    incomes = incomeStats.map { it.toTransactionStat() },
    expenses = expenseStats.map { it.toTransactionStat() },
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
)

private fun StatItemDto.toTransactionStat() = TransactionStat(
    categoryId = categoryId, categoryName = categoryName, emoji = emoji, amount = amount
)

private fun AccountResponse.toAccount(): Account = Account(
    id = id,
    userId = userId,
    name = name,
    balance = balance,
    currency = currency,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
)

fun AccountBrief.toAccountCreateRequest() = AccountCreateRequest(
    name = name, balance = balance, currency = currency
)