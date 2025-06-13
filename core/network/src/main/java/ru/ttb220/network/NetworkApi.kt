package ru.ttb220.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ttb220.network.model.request.AccountCreateRequest
import ru.ttb220.network.model.request.TransactionCreateRequest
import ru.ttb220.network.model.response.AccountDetailedResponse
import ru.ttb220.network.model.response.AccountHistoryResponse
import ru.ttb220.network.model.response.AccountResponse
import ru.ttb220.network.model.Category
import ru.ttb220.network.model.response.TransactionDetailedResponse
import ru.ttb220.network.model.response.TransactionResponse

internal interface NetworkApi {
    @GET("accounts")
    suspend fun getAllAccounts(): List<AccountResponse>

    @POST("accounts")
    suspend fun createNewAccount(
        @Body accountCreateRequest: AccountCreateRequest
    ): AccountResponse

    @GET("accounts")
    suspend fun getAccountById(
        @Query("id") id: Int
    ): AccountDetailedResponse

    @POST("accounts")
    suspend fun updateAccountById(
        @Body accountCreateRequest: AccountCreateRequest
    ): AccountResponse

    @GET("accounts/{id}/history")
    suspend fun getAccountHistoryById(
        @Path("id") id: Int
    ): AccountHistoryResponse


    @GET("categories")
    suspend fun getAllCategories(): List<Category>

    @GET("categories/type/{isIncome}")
    suspend fun getAllCategoriesByType(
        @Path("isIncome") isIncome: Boolean
    ): List<Category>


    @POST("transactions")
    suspend fun createNewTransaction(
        @Body transactionCreateRequest: TransactionCreateRequest
    ): TransactionResponse

    @GET("transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") id: Int
    ) : TransactionDetailedResponse
}