package ru.ttb220.datastore

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
class DataStoreInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var preferencesDataSource: PreferencesDataSource

    @Before
    fun setup() {
        hiltRule.inject()
    }

    // FIXME: stucks
    @Test
    fun test() = runBlocking {
        preferencesDataSource.setActiveAccountId(54).collect {}
        preferencesDataSource.getActiveAccountId().collect {
            assert(it == 54)
        }

        preferencesDataSource.setActiveAccountId(12).collect {}
        preferencesDataSource.getActiveAccountId().collect {
            assert(it == 12)
        }
    }
}