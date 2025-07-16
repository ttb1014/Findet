package ru.ttb220.data.impl.legacy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import ru.ttb220.data.api.legacy.TransactionsRepository
import ru.ttb220.data.impl.util.withRetry
import ru.ttb220.data.impl.util.wrapToSafeResult
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.Transaction
import ru.ttb220.model.transaction.TransactionBrief
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.network.api.RemoteDataSource
import ru.ttb220.network.api.model.request.TransactionCreateRequestDto
import ru.ttb220.network.api.model.request.TransactionUpdateRequestDto
import ru.ttb220.network.api.model.response.toTransaction
import ru.ttb220.network.api.model.response.toTransactionDetailed
import javax.inject.Inject

class OnlineTransactionsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : TransactionsRepository {

    override fun createNewTransaction(transaction: TransactionBrief): Flow<SafeResult<Transaction>> =
        flow<Transaction> {
            emit(
                remoteDataSource.createNewTransaction(transaction.toTransactionCreateRequestDto())
                    .toTransaction()
            )
        }.withRetry()
            .wrapToSafeResult()

    override fun getTransactionById(id: Int): Flow<SafeResult<TransactionDetailed>> =
        flow<TransactionDetailed> {
            emit(remoteDataSource.getTransactionById(id).toTransactionDetailed())
        }.withRetry()
            .wrapToSafeResult()

    override fun updateTransactionById(
        id: Int, transaction: TransactionBrief
    ): Flow<SafeResult<TransactionDetailed>> = flow<TransactionDetailed> {
        emit(
            remoteDataSource.updateTransactionById(id, transaction.toTransactionUpdateRequestDto())
                .toTransactionDetailed()
        )
    }.withRetry()
        .wrapToSafeResult()

    override fun deleteTransactionById(id: Int): Flow<SafeResult<Unit>> = flow<Unit> {
        emit(remoteDataSource.deleteTransactionById(id))
    }.withRetry()
        .wrapToSafeResult()

    override fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Flow<SafeResult<List<TransactionDetailed>>> = flow<List<TransactionDetailed>> {
        emit(remoteDataSource.getAccountTransactionsForPeriod(
            accountId, startDate, endDate
        ).map { it.toTransactionDetailed() })
    }.withRetry()
        .wrapToSafeResult()
}

private fun TransactionBrief.toTransactionCreateRequestDto(): TransactionCreateRequestDto =
    TransactionCreateRequestDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )

private fun TransactionBrief.toTransactionUpdateRequestDto(): TransactionUpdateRequestDto =
    TransactionUpdateRequestDto(
        accountId = accountId,
        categoryId = categoryId,
        amount = amount,
        transactionDate = transactionDate,
        comment = comment
    )