package ru.ttb220.security

import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
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
        dataSource.setUserId("user-123")
        val result = dataSource.getUserId()
        assertEquals("user-123", result)
    }

    @Test
    fun testIsUserIdSet() {
        dataSource.setUserId("user-123")
        assertTrue(dataSource.isUserIdSet())
    }

    @Test
    fun testClearUserId() {
        dataSource.setUserId("user-123")
        dataSource.clearUserId()
        assertNull(dataSource.getUserId())
        assertFalse(dataSource.isUserIdSet())
    }
}