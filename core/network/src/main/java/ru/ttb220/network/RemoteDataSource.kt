package ru.ttb220.network

import kotlinx.datetime.Instant
import ru.ttb220.network.model.CategoryDto
import ru.ttb220.network.model.request.AccountCreateRequest
import ru.ttb220.network.model.request.TransactionCreateRequest
import ru.ttb220.network.model.request.TransactionUpdateRequest
import ru.ttb220.network.model.response.AccountDetailedResponse
import ru.ttb220.network.model.response.AccountHistoryResponse
import ru.ttb220.network.model.response.AccountResponse
import ru.ttb220.network.model.response.TransactionDetailedResponse
import ru.ttb220.network.model.response.TransactionResponse

/**
 * Абстракция над HTTP запросами. Методы выбрасывают исключения
 * @throws ApiException
 * @throws JsonDecodingException
 * @see HttpCodeInterceptor
 * @see AuthInterceptor
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

    suspend fun deleteAccountById(
        id: Int
    )

    suspend fun getAccountHistoryById(
        id: Int
    ): AccountHistoryResponse

    suspend fun getAllCategories(): List<CategoryDto>

    suspend fun getAllCategoriesByType(
        isIncome: Boolean
    ): List<CategoryDto>

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

    /**
     * Either deletes transaction or throws an exception
     */
    suspend fun deleteTransactionById(
        id: Int,
    )

    suspend fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: Instant? = null,
        endDate: Instant? = null,
    ): List<TransactionDetailedResponse>
}