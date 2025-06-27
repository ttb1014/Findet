package ru.ttb220.network

import kotlinx.datetime.LocalDate
import ru.ttb220.network.model.CategoryDto
import ru.ttb220.network.model.request.AccountCreateRequestDto
import ru.ttb220.network.model.request.TransactionCreateRequestDto
import ru.ttb220.network.model.request.TransactionUpdateRequestDto
import ru.ttb220.network.model.response.AccountDetailedResponseDto
import ru.ttb220.network.model.response.AccountHistoryResponseDto
import ru.ttb220.network.model.response.AccountResponseDto
import ru.ttb220.network.model.response.TransactionDetailedResponseDto
import ru.ttb220.network.model.response.TransactionResponseDto

/**
 * Abstraction over network requests
 * @throws ApiException and its inheritors
 * @throws JsonDecodingException
 */
interface RemoteDataSource {
    suspend fun getAllAccounts(): List<AccountResponseDto>

    suspend fun createNewAccount(
        accountCreateRequestDto: AccountCreateRequestDto
    ): AccountResponseDto

    suspend fun getAccountById(
        id: Int
    ): AccountDetailedResponseDto

    suspend fun updateAccountById(
        id: Int,
        accountCreateRequestDto: AccountCreateRequestDto
    ): AccountResponseDto

    suspend fun deleteAccountById(
        id: Int
    )

    suspend fun getAccountHistoryById(
        id: Int
    ): AccountHistoryResponseDto

    suspend fun getAllCategories(): List<CategoryDto>

    suspend fun getAllCategoriesByType(
        isIncome: Boolean
    ): List<CategoryDto>

    suspend fun createNewTransaction(
        transactionCreateRequestDto: TransactionCreateRequestDto
    ): TransactionResponseDto

    suspend fun getTransactionById(
        id: Int
    ): TransactionDetailedResponseDto

    suspend fun updateTransactionById(
        id: Int,
        transactionUpdateRequestDto: TransactionUpdateRequestDto
    ): TransactionDetailedResponseDto

    /**
     * Either deletes transaction or throws an exception
     */
    suspend fun deleteTransactionById(
        id: Int,
    )

    suspend fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: LocalDate? = null,
        endDate: LocalDate? = null,
    ): List<TransactionDetailedResponseDto>
}