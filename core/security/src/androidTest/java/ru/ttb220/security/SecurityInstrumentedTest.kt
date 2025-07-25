package ru.ttb220.security

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.ttb220.security.di.TestApplication
import ru.ttb220.security.impl.EncryptedPreferencesDataSourceImpl
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class SecurityInstrumentedTest {

    @Inject
    lateinit var dataSource: EncryptedPreferencesDataSourceImpl

    @Before
    fun setUp() {
        val app = ApplicationProvider.getApplicationContext<TestApplication>()
        app.testComponent.inject(this)
    }

    @Test
    fun testSetAndGetUserId() {
        dataSource.setUserId(54)
        val result = dataSource.getUserId()
        assertEquals(54, result)
    }

    @Test
    fun testIsUserIdSet() {
        dataSource.setUserId(54)
        assertTrue(dataSource.isUserIdSet())
    }

    @Test
    fun testClearUserId() {
        dataSource.setUserId(54)
        dataSource.clearUserId()
        assertNull(dataSource.getUserId())
        assertFalse(dataSource.isUserIdSet())
    }

    @Test
    fun observeUserId_emits_value_on_every_change() = runBlocking {
        val collected = mutableListOf<Int>()

        val job = launch {
            dataSource.observeUserId()
                .take(3)
                .toList(collected)
        }

        delay(100)

        dataSource.setUserId(101)
        delay(50)
        dataSource.setUserId(202)
        delay(50)

        job.join()

        assertTrue(collected.containsAll(listOf(101, 202)))
        assertTrue(collected.size == 3)
    }

    @Test
    fun flow_does_not_emit_previous_values_if_collected_after_changes() = runBlocking {
        dataSource.clearUserId()

        dataSource.setUserId(42)
        dataSource.setUserId(84)

        val collected = mutableListOf<Int>()
        val job = launch {
            dataSource.observeUserId()
                .onEach { collected.add(it) }
                .launchIn(this)
        }

        delay(50)

        job.cancel()

        assertFalse(collected.contains(42))
        assertTrue(collected.contains(84))
    }
}