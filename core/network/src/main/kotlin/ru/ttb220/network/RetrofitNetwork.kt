package ru.ttb220.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ttb220.network.model.CategoryDto
import ru.ttb220.network.model.request.AccountCreateRequest
import ru.ttb220.network.model.request.TransactionCreateRequest
import ru.ttb220.network.model.request.TransactionUpdateRequest
import ru.ttb220.network.model.response.AccountDetailedResponse
import ru.ttb220.network.model.response.AccountHistoryResponse
import ru.ttb220.network.model.response.AccountResponse
import ru.ttb220.network.model.response.TransactionDetailedResponse
import ru.ttb220.network.model.response.TransactionResponse
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Все доступные запросы к серверу
 */
internal interface NetworkApi {
    @GET("accounts")
    suspend fun getAllAccounts(): List<AccountResponse>

    @POST("accounts")
    suspend fun createNewAccount(
        @Body accountCreateRequest: AccountCreateRequest
    ): AccountResponse

    @GET("accounts/{id}")
    suspend fun getAccountById(
        @Path("id") id: Int
    ): AccountDetailedResponse

    @POST("accounts/{id}")
    suspend fun updateAccountById(
        @Path("id") id: Int,
        @Body accountCreateRequest: AccountCreateRequest
    ): AccountResponse

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(
        @Path("id") id: Int,
    ): Unit

    @GET("accounts/{id}/history")
    suspend fun getAccountHistoryById(
        @Path("id") id: Int
    ): AccountHistoryResponse


    @GET("categories")
    suspend fun getAllCategories(): List<CategoryDto>

    @GET("categories/type/{isIncome}")
    suspend fun getAllCategoriesByType(
        @Path("isIncome") isIncome: Boolean
    ): List<CategoryDto>


    @POST("transactions")
    suspend fun createNewTransaction(
        @Body transactionCreateRequest: TransactionCreateRequest
    ): TransactionResponse

    @GET("transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") id: Int
    ): TransactionDetailedResponse

    @POST("transactions/{id}")
    suspend fun updateTransactionById(
        @Path("id") id: Int,
        @Body transactionUpdateRequest: TransactionUpdateRequest
    ): TransactionDetailedResponse

    @DELETE("transactions/{id}")
    suspend fun deleteTransactionById(
        @Path("id") id: Int,
    )

    @GET("transactions/account/{accountId}/period")
    suspend fun getAccountTransactionsForPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: LocalDate?,
        @Query("endDate") endDate: LocalDate?,
    ): List<TransactionDetailedResponse>
}

@Singleton
internal class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : RemoteDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(DEFAULT_API_URL)
        // Фабирка запросов к сети. Токен подставляется автоматически.
        .callFactory {
            okhttpCallFactory.get().newCall(it)
        }
        // Конвертер из/в JSON Kotlin-классов
        .addConverterFactory(
            networkJson.asConverterFactory(
                DEFAULT_MEDIA_TYPE.toMediaType()
            )
        )
        .build()
        .create(NetworkApi::class.java)

    companion object {
        const val DEFAULT_API_URL = BuildConfig.BACKEND_URL
        const val DEFAULT_MEDIA_TYPE = "application/json"
    }

    override suspend fun getAllAccounts(): List<AccountResponse> =
        networkApi.getAllAccounts()

    override suspend fun createNewAccount(accountCreateRequest: AccountCreateRequest): AccountResponse =
        networkApi.createNewAccount(accountCreateRequest)

    override suspend fun getAccountById(id: Int): AccountDetailedResponse =
        networkApi.getAccountById(id)

    override suspend fun updateAccountById(
        id: Int,
        accountCreateRequest: AccountCreateRequest
    ): AccountResponse =
        networkApi.updateAccountById(id, accountCreateRequest)

    override suspend fun deleteAccountById(id: Int) =
        networkApi.deleteAccountById(id)

    override suspend fun getAccountHistoryById(id: Int): AccountHistoryResponse =
        networkApi.getAccountHistoryById(id)

    override suspend fun getAllCategories(): List<CategoryDto> =
        networkApi.getAllCategories()

    override suspend fun getAllCategoriesByType(isIncome: Boolean): List<CategoryDto> =
        networkApi.getAllCategoriesByType(isIncome)

    override suspend fun createNewTransaction(transactionCreateRequest: TransactionCreateRequest): TransactionResponse =
        networkApi.createNewTransaction(transactionCreateRequest)

    override suspend fun getTransactionById(id: Int): TransactionDetailedResponse =
        networkApi.getTransactionById(id)

    override suspend fun updateTransactionById(
        id: Int,
        transactionUpdateRequest: TransactionUpdateRequest
    ): TransactionDetailedResponse =
        networkApi.updateTransactionById(id, transactionUpdateRequest)

    override suspend fun deleteTransactionById(id: Int) =
        networkApi.deleteTransactionById(id)

    override suspend fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): List<TransactionDetailedResponse> =
        networkApi.getAccountTransactionsForPeriod(
            accountId,
            startDate,
            endDate
        )
}