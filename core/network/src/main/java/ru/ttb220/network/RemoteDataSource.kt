package ru.ttb220.network

import kotlinx.datetime.LocalDateTime
import ru.ttb220.network.model.Category
import ru.ttb220.network.model.request.AccountCreateRequest
import ru.ttb220.network.model.request.TransactionCreateRequest
import ru.ttb220.network.model.request.TransactionUpdateRequest
import ru.ttb220.network.model.response.AccountDetailedResponse
import ru.ttb220.network.model.response.AccountHistoryResponse
import ru.ttb220.network.model.response.AccountResponse
import ru.ttb220.network.model.response.TransactionDetailedResponse
import ru.ttb220.network.model.response.TransactionResponse

/**
 * Abstraction over HTTP requests
 */
interface RemoteDataSource {
    suspend fun getAllAccounts(): List<AccountResponse>

    suspend fun createNewAccount(
        accountCreateRequest: AccountCreateRequest
    ): AccountResponse

    suspend fun getAccountById(
        id: Int
    ): AccountDetailedResponse

    suspend fun updateAccountById(
        id: Int,
        accountCreateRequest: AccountCreateRequest
    ): AccountResponse

    suspend fun getAccountHistoryById(
        id: Int
    ): AccountHistoryResponse

    suspend fun getAllCategories(): List<Category>

    suspend fun getAllCategoriesByType(
        isIncome: Boolean
    ): List<Category>

    suspend fun createNewTransaction(
        transactionCreateRequest: TransactionCreateRequest
    ): TransactionResponse

    suspend fun getTransactionById(
        id: Int
    ): TransactionDetailedResponse

    suspend fun updateTransactionById(
        id: Int,
        transactionUpdateRequest: TransactionUpdateRequest
    ): TransactionDetailedResponse

    suspend fun deleteTransactionById(
        id: Int,
    )

    suspend fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
    ): List<TransactionDetailedResponse>
}