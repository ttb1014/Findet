package ru.ttb220.bottomsheet

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.ttb220.bottomsheet.domain.SetActiveAccountCurrencyUseCase
import ru.ttb220.model.SafeResult
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UseCaseTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    internal lateinit var setActiveAccountCurrencyUseCase: SetActiveAccountCurrencyUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testUseCase() = runBlocking {
        setActiveAccountCurrencyUseCase.invoke("USD").collect {
            assert(it is SafeResult.Success)
        }
    }

    companion object {
        const val TEST_TAG = "TEST"
    }
}