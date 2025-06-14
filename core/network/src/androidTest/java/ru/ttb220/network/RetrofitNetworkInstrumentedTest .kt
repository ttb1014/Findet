package ru.ttb220.network

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RetrofitNetworkInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var retrofitNetwork: RetrofitNetwork

    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun getAllAccountsSuccess() = runBlocking {
        val accounts = remoteDataSource.getAllAccounts()
    }

    @Test
    fun getAllTransactionsForThisMonth() = runBlocking {
        val accounts = remoteDataSource.getAllAccounts()
        accounts.forEach { account ->
            val transactions = remoteDataSource.getAccountTransactionsForPeriod(
                account.id
            )
        }
    }

    companion object {
        private const val TAG = "Retrofit test"
    }
}