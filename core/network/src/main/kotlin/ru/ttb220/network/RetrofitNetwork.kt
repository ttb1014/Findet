package ru.ttb220.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import ru.ttb220.network.model.CategoryDto
import ru.ttb220.network.model.request.AccountCreateRequestDto
import ru.ttb220.network.model.request.TransactionCreateRequestDto
import ru.ttb220.network.model.request.TransactionUpdateRequestDto
import ru.ttb220.network.model.response.AccountDetailedResponseDto
import ru.ttb220.network.model.response.AccountHistoryResponseDto
import ru.ttb220.network.model.response.AccountResponseDto
import ru.ttb220.network.model.response.TransactionDetailedResponseDto
import ru.ttb220.network.model.response.TransactionResponseDto
import javax.inject.Inject
import javax.inject.Singleton

/**
 * List of all available endpoints
 */
internal interface NetworkApi {
    @GET("accounts")
    suspend fun getAllAccounts(): List<AccountResponseDto>

    @POST("accounts")
    suspend fun createNewAccount(
        @Body accountCreateRequestDto: AccountCreateRequestDto
    ): AccountResponseDto

    @GET("accounts/{id}")
    suspend fun getAccountById(
        @Path("id") id: Int
    ): AccountDetailedResponseDto

    @PUT("accounts/{id}")
    suspend fun updateAccountById(
        @Path("id") id: Int,
        @Body accountCreateRequestDto: AccountCreateRequestDto
    ): AccountResponseDto

    @DELETE("accounts/{id}")
    suspend fun deleteAccountById(
        @Path("id") id: Int,
    ): Unit

    @GET("accounts/{id}/history")
    suspend fun getAccountHistoryById(
        @Path("id") id: Int
    ): AccountHistoryResponseDto


    @GET("categories")
    suspend fun getAllCategories(): List<CategoryDto>

    @GET("categories/type/{isIncome}")
    suspend fun getAllCategoriesByType(
        @Path("isIncome") isIncome: Boolean
    ): List<CategoryDto>


    @POST("transactions")
    suspend fun createNewTransaction(
        @Body transactionCreateRequestDto: TransactionCreateRequestDto
    ): TransactionResponseDto

    @GET("transactions/{id}")
    suspend fun getTransactionById(
        @Path("id") id: Int
    ): TransactionDetailedResponseDto

    @POST("transactions/{id}")
    suspend fun updateTransactionById(
        @Path("id") id: Int,
        @Body transactionUpdateRequestDto: TransactionUpdateRequestDto
    ): TransactionDetailedResponseDto

    @DELETE("transactions/{id}")
    suspend fun deleteTransactionById(
        @Path("id") id: Int,
    )

    @GET("transactions/account/{accountId}/period")
    suspend fun getAccountTransactionsForPeriod(
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: LocalDate?,
        @Query("endDate") endDate: LocalDate?,
    ): List<TransactionDetailedResponseDto>
}

@Singleton
internal class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : RemoteDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(DEFAULT_API_URL)
        // Token is injected automatically
        .callFactory {
            okhttpCallFactory.get().newCall(it)
        }
        // Converts JSON to Kotlin class and vice versa
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

    override suspend fun getAllAccounts(): List<AccountResponseDto> =
        networkApi.getAllAccounts()

    override suspend fun createNewAccount(accountCreateRequestDto: AccountCreateRequestDto): AccountResponseDto =
        networkApi.createNewAccount(accountCreateRequestDto)

    override suspend fun getAccountById(id: Int): AccountDetailedResponseDto =
        networkApi.getAccountById(id)

    override suspend fun updateAccountById(
        id: Int,
        accountCreateRequestDto: AccountCreateRequestDto
    ): AccountResponseDto =
        networkApi.updateAccountById(id, accountCreateRequestDto)

    override suspend fun deleteAccountById(id: Int) =
        networkApi.deleteAccountById(id)

    override suspend fun getAccountHistoryById(id: Int): AccountHistoryResponseDto =
        networkApi.getAccountHistoryById(id)

    override suspend fun getAllCategories(): List<CategoryDto> =
        networkApi.getAllCategories()

    override suspend fun getAllCategoriesByType(isIncome: Boolean): List<CategoryDto> =
        networkApi.getAllCategoriesByType(isIncome)

    override suspend fun createNewTransaction(
        transactionCreateRequestDto: TransactionCreateRequestDto
    ): TransactionResponseDto =
        networkApi.createNewTransaction(transactionCreateRequestDto)

    override suspend fun getTransactionById(id: Int): TransactionDetailedResponseDto =
        networkApi.getTransactionById(id)

    override suspend fun updateTransactionById(
        id: Int,
        transactionUpdateRequestDto: TransactionUpdateRequestDto
    ): TransactionDetailedResponseDto =
        networkApi.updateTransactionById(id, transactionUpdateRequestDto)

    override suspend fun deleteTransactionById(id: Int) =
        networkApi.deleteTransactionById(id)

    override suspend fun getAccountTransactionsForPeriod(
        accountId: Int,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): List<TransactionDetailedResponseDto> =
        networkApi.getAccountTransactionsForPeriod(
            accountId,
            startDate,
            endDate
        )
}