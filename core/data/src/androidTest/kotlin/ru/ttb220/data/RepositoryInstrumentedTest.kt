package ru.ttb220.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.CategoriesRepository
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.data.impl.util.withRetry
import ru.ttb220.data.impl.util.wrapToSafeResult
import ru.ttb220.model.SafeResult
import ru.ttb220.network.exception.NotFoundException
import ru.ttb220.network.exception.ServerErrorException
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RepositoryInstrumentedTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var transactionsRepository: TransactionsRepository

    @Inject
    internal lateinit var accountsRepository: AccountsRepository

    @Inject
    internal lateinit var categoriesRepository: CategoriesRepository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testWithRetry() = runBlocking {
        var int = 1
        val flow = flow {
            delay(100L)
            emit(int++)
            if (int != 3)
                throw ServerErrorException(
                    500
                )
        }.withRetry()
        flow.collect {
            println(it)
        }
    }

    @Test
    fun testWrapToResultError() = runBlocking {
        flow<Unit> {
            throw ServerErrorException(
                500
            )
        }.withRetry()
            .wrapToSafeResult()
            .collect {
                println(it)
            }
    }

    @Test
    fun testWrapToResultSuccess() = runBlocking {
        flow {
            delay(100L)
            emit(1)
        }.withRetry()
            .wrapToSafeResult()
            .collect {
                println(it)
            }
    }

    @Test
    fun testGetAllAccounts() = runBlocking {
        val flow = accountsRepository.getAllAccounts()
        flow.collect { account ->
            println(account)
        }
    }

    @Test
    fun testGetTransactions() = runBlocking {
        accountsRepository.getAllAccounts().collect { accountsResult ->
            val accounts = (accountsResult as SafeResult.Success).data

            accounts.forEach { account ->
                transactionsRepository.getAccountTransactionsForPeriod(
                    accountId = account.id
                ).collect { transactions ->
                    assertNotNull(transactions)
                }
            }
        }
    }

    @Test
    fun testGetAllTransactionsSuccess() = runBlocking {
        val flow = accountsRepository.getAllAccounts()
        flow.collect { accountsResult ->
            val accounts = (accountsResult as SafeResult.Success).data

            val deferredTransactions = accounts.map { account ->
                transactionsRepository.getAccountTransactionsForPeriod(
                    accountId = account.id
                )
            }
            deferredTransactions.forEach {
                it.collect { transactions ->
                }
            }

            runBlocking {
                transactionsRepository.getAccountTransactionsForPeriod(
                    accountId = -111
                ).collect { transactions ->
                }
            }
        }
    }

    @Test
    fun testGetAccountByFaultyId() = runBlocking {
        val faultyId = -1
        val flow = accountsRepository.getAccountById(faultyId)
        flow
            .catch { throwable ->
                assert(throwable is NotFoundException)
                println("successfully caught 404")
            }
            .collect {
                println(it)
            }
    }

    companion object {
        const val TEST_TAG = "TEST"
    }
}