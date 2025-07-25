package ru.ttb220.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.CategoriesRepository
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.data.di.TestApplication
import ru.ttb220.database.AppDatabase
import ru.ttb220.database.dao.AccountsDao
import ru.ttb220.database.dao.CategoriesDao
import ru.ttb220.database.dao.TransactionsDao
import ru.ttb220.database.mapper.toAccountEntity
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.model.mapper.toAccountBrief
import ru.ttb220.network.api.RemoteDataSource
import ru.ttb220.network.api.mapper.toAccount
import ru.ttb220.network.api.mapper.toAccountCreateRequestDto
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

@RunWith(AndroidJUnit4::class)
class DataInstrumentedTest {

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var accountsDao: AccountsDao

    @Inject
    lateinit var transactionsDao: TransactionsDao

    @Inject
    lateinit var categoriesDao: CategoriesDao

    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    @Inject
    lateinit var accountsRepository: AccountsRepository

    @Inject
    lateinit var transactionsRepository: TransactionsRepository

    @Inject
    lateinit var categoriesRepository: CategoriesRepository

    private fun RemoteDataSource.clearAll() = runBlocking {
        val accounts = getAllAccounts().map { it.toAccount() }
        accounts.map { account ->
            val transactions = getAccountTransactionsForPeriod(
                account.id,
                LocalDate.parse("0000-01-01"),
                LocalDate.parse("9999-12-31"),
            )
            transactions.map { transaction ->
                launch {
                    deleteTransactionById(transaction.id)
                }
            }.joinAll()
            launch {
                deleteAccountById(account.id)
            }
        }.joinAll()
    }

    private val userId = 54

    class TestSynchronizer : Synchronizer

    @Before
    fun setUp() = runBlocking {
        val app = ApplicationProvider.getApplicationContext<TestApplication>()
        app.testComponent.inject(this@DataInstrumentedTest)

        db.clearAllTables()
        remoteDataSource.clearAll()
    }

    @Test
    fun sync_whenOnlyRemoteHasData_insertsToLocal() = runBlocking {
        val accountRequest = AccountBrief(
            name = "Remote",
            balance = 600_000.00,
            currency = "RUB",
        ).toAccountCreateRequestDto()

        val response = remoteDataSource.createNewAccount(accountRequest).toAccount()

        val success = (accountsRepository as Syncable).syncWith(TestSynchronizer())
        val localAccount = accountsDao.getAccountById(response.id.toLong()).first()

        assertTrue(success)
        assertNotNull(localAccount)
        assertEquals(accountRequest.name, localAccount?.name)
    }

    @Test
    fun sync_whenBothHaveData_remoteIsNewer_updatesLocal() = runBlocking {
        val local =
            Account(
                id = 1,
                name = "Local",
                updatedAt = now() - 1.toLong().days,
                createdAt = now(),
                userId = userId,
                balance = 600_000.00,
                currency = "RUB"
            )
        val remote = local.copy(name = "Remote", updatedAt = now())

        accountsDao.insertAccount(local.toAccountEntity())
        val remoteResponse = remoteDataSource.createNewAccount(remote.toAccountCreateRequestDto())

        (accountsRepository as Syncable).syncWith(TestSynchronizer())

        val updated = accountsDao.getAccountById(remoteResponse.id.toLong()).first()
        assertEquals("Remote", updated?.name)
    }

    @Test
    fun sync_whenBothHaveData_localIsNewer_updatesRemote() = runBlocking {
        val local = Account(
            id = 1,
            name = "Local",
            updatedAt = now(),
            createdAt = now(),
            userId = userId,
            balance = 600_000.00,
            currency = "RUB"
        )
        val remote = local.copy(
            name = "Remote",
            updatedAt = now() - 1.toLong().days
        )

        val remoteId = remoteDataSource.createNewAccount(remote.toAccountCreateRequestDto()).id
        val localId = accountsDao.insertAccount(local.copy(id = remoteId).toAccountEntity())

        (accountsRepository as Syncable).syncWith(TestSynchronizer())

        val updatedRemote = remoteDataSource.getAccountById(remoteId)
        assertEquals("Remote", updatedRemote.name)
    }

    @Test
    fun sync_whenLocalHasDataAndRemoteDoesNot_deletesLocal() = runBlocking {
        val account = Account(
            id = 1,
            name = "OnlyLocal",
            updatedAt = now(),
            createdAt = now(),
            userId = userId,
            balance = 600_000.00,
            currency = "RUB"
        )
        accountsDao.insertAccount(account.toAccountEntity())

        (accountsRepository as Syncable).syncWith(TestSynchronizer())

        val deleted = accountsDao.getAccountById(1).first()
        assertNull(deleted)
    }

    @Test
    fun repositoryOperations_insertUpdateDelete() = runBlocking {
        val account = Account(
            id = 1,
            name = "Initial",
            updatedAt = now(),
            createdAt = now(),
            userId = userId,
            balance = 600_000.00,
            currency = "RUB"
        )

        // insert
        val created = accountsRepository.createNewAccount(account.toAccountBrief()).last()
        assertTrue(created is SafeResult.Success)

        // update
        val updated = accountsRepository.updateAccountById(
            (created as SafeResult.Success).data.id,
            AccountBrief(
                name = "Updated",
                balance = 700_000.00,
                currency = "USD"
            )
        ).first()
        assertTrue(updated is SafeResult.Success)
        assertEquals("Updated", (updated as SafeResult.Success).data.name)

        // get
        val loaded = accountsRepository.getAccountById(created.data.id).first()
        assertTrue(loaded is SafeResult.Success)

        // delete
        val deleted = accountsRepository.deleteAccountById(created.data.id).first()
        assertTrue(deleted is SafeResult.Success)
        assertNull(accountsDao.getAccountById(1).first())
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.ttb220.data.test", appContext.packageName)
    }
}