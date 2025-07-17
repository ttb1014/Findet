package ru.ttb220.network

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.ttb220.network.api.RemoteDataSource
import ru.ttb220.network.api.exception.ApiException
import ru.ttb220.network.api.model.request.AccountCreateRequestDto
import ru.ttb220.network.api.model.request.TransactionCreateRequestDto
import ru.ttb220.network.test.R
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class RetrofitNetworkInstrumentedTest {

    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @Inject
    internal lateinit var json: Json

    @Before
    fun setup() {
        val app = ApplicationProvider.getApplicationContext<TestApplication>()
        app.testComponent.inject(this)
    }

    @Test
    fun updateAccount(): Unit = runBlocking {
        remoteDataSource.updateAccountById(
            id = 54,
            accountCreateRequestDto = AccountCreateRequestDto(
                name = "NASDNMAS",
                balance = "-6000000",
                currency = "USD"
            )
        )
    }

    @Test
    fun getAllAccountsSuccess() = runBlocking {
        val accounts = remoteDataSource.getAllAccounts()
    }

    @Test
    fun handles404() = runBlocking {
        try {
            remoteDataSource.getAccountById(-1)
            fail("Ожидалась ошибка")
        } catch (e: ApiException) {
            println("Обработалcя код ${e.code}: ${e.message}")
        }
    }

    @Test
    fun getAllTransactionsForThisMonth() = runBlocking {
        val accounts = remoteDataSource.getAllAccounts()
        accounts.forEach { account ->
            val transactions = remoteDataSource.getAccountTransactionsForPeriod(
                account.id
            )
            println(transactions)
        }
    }

    private fun deleteAllOldTransactionsForAccount(accountID: Int) = runBlocking {
        val transactions = remoteDataSource.getAccountTransactionsForPeriod(
            accountID,
            LocalDate.parse("0000-01-01"),
            LocalDate.parse("9999-12-31")
        )
        transactions.forEach { transaction ->
            remoteDataSource.deleteTransactionById(transaction.id)
        }
    }

    @Test
    fun deleteAllTransactions() = runBlocking {
        val accountID = remoteDataSource.getAllAccounts()[0].id

        deleteAllOldTransactionsForAccount(accountID)
    }

    @Test
    fun deleteOldAndCreateFakeTransactions() = runBlocking {
        val accountID = remoteDataSource.getAllAccounts()[0].id

        deleteAllOldTransactionsForAccount(accountID)

        val context = InstrumentationRegistry.getInstrumentation().context
        val text = context.resources.openRawResource(R.raw.transactions)
            .bufferedReader().use { it.readText() }
        val transactions: List<TransactionCreateRequestDto> =
            json.decodeFromString<List<TransactionCreateRequestDto>>(text)
                .map {
                    it.copy(accountId = accountID)
                }

        for (transaction in transactions) {
            remoteDataSource.createNewTransaction(transaction)
        }
    }
}