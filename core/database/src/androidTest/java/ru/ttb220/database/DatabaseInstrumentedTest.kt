package ru.ttb220.database

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.ttb220.database.dao.AccountsDao
import ru.ttb220.database.dao.CategoriesDao
import ru.ttb220.database.dao.TransactionsDao
import ru.ttb220.database.model.entity.AccountEntity
import java.time.Instant
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class DatabaseInstrumentedTest {

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var accountsDao: AccountsDao

    @Inject
    lateinit var transactionsDao: TransactionsDao

    @Inject
    lateinit var categoriesDao: CategoriesDao

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<TestApplication>()
        app.testComponent.inject(this)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testAccountsDao() = runBlocking {
        val uiAutomation = InstrumentationRegistry.getInstrumentation().uiAutomation
        uiAutomation.executeShellCommand("svc wifi enable")
        uiAutomation.executeShellCommand("svc data enable")
        accountsDao.insertAccount(
            AccountEntity(
                id = 54,
                name = "Tinkoff",
                balance = 123400.00,
                currency = "RUB",
                createdAt = Instant.now(),
                updatedAt = Instant.now()
            )
        )
        assert(accountsDao.getAccountById(54).first()!!.name == "Tinkoff")
    }
}