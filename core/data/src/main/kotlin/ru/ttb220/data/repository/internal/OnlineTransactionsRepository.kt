package ru.ttb220.data.repository.internal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import ru.ttb220.data.repository.TransactionsRepository
import ru.ttb220.data.util.withRetry
import ru.ttb220.model.Category
import ru.ttb220.model.account.AccountState
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.network.RemoteDataSource
import ru.ttb220.network.model.AccountDto
import ru.ttb220.network.model.CategoryDto
import ru.ttb220.network.model.request.TransactionCreateRequest
import ru.ttb220.network.model.request.TransactionUpdateRequest
import ru.ttb220.network.model.response.TransactionDetailedResponse
import ru.ttb220.network.model.response.TransactionResponse
import javax.inject.Inject

internal class OnlineTransactionsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TransactionsRepository {

    override fun createNewTransaction(transaction: TransactionBrief): Flow<Transaction> =
        flow<Transaction> {
            emit(
                remoteDataSource.createNewTransaction(transaction.toTransactionCreateRequest())
                    .toTransaction()
            )
        }.withRetry()

    override fun getTransactionById(id: Int): Flow<TransactionDetailed> =
        flow<TransactionDetailed> {
            emit(remoteDataSource.getTransactionById(id).toTransactionDetailed())
        }.withRetry()

    override fun updateTransactionById(
        id: Int, transaction: TransactionBrief
    ): Flow<TransactionDetailed> = flow<TransactionDetailed> {
        emit(
            remoteDataSource.updateTransactionById(id, transaction.toTransactionUpdateRequest())
                .toTransactionDetailed()
        )
    }.withRetry()

    override fun deleteTransactionById(id: Int): Flow<Unit> = flow<Unit> {
        emit(remoteDataSource.deleteTransactionById(id))
    }.withRetry()

    override fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?
    ): Flow<List<TransactionDetailed>> = flow<List<TransactionDetailed>> {
        emit(remoteDataSource.getAccountTransactionsForPeriod(
            accountId, startDate, endDate
        ).map { it.toTransactionDetailed() })
    }.withRetry()
}

private fun TransactionDetailedResponse.toTransactionDetailed(): TransactionDetailed =
    TransactionDetailed(
        id = id,
        account = accountDto.toAccountState(),
        category = categoryDto.toCategory(),
        amount = amount,
        transactionDate = transactionDate,
        comment = comment,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

private fun CategoryDto.toCategory(): Category = Category(
    id = id,
    name = name,
    emoji = emoji,
    isIncome = isIncome
)

private fun AccountDto.toAccountState(): AccountState = AccountState(
    id = id,
    name = name,
    balance = balance,
    currency = currency,
)

private fun TransactionResponse.toTransaction(): Transaction = Transaction(
    id = id,
    accountId = accountId,
    categoryId = categoryId,
    amount = amount,
    transactionDate = transactionDate,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt
)

private fun TransactionBrief.toTransactionCreateRequest(): TransactionCreateRequest =
    TransactionCreateRequest(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )

private fun TransactionBrief.toTransactionUpdateRequest(): TransactionUpdateRequest =
    TransactionUpdateRequest(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )