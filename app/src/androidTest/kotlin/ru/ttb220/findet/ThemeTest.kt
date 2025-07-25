package ru.ttb220.findet

import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import ru.ttb220.findet.util.appComponent
import ru.ttb220.model.ThemeState

// все тесты здесь падают с NullPointerException)
// FIXME: NullPointerException at ru.ttb220.findet.MainActivity.onCreate$lambda$3$lambda$2(MainActivity.kt:48)
class ThemeTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun darkModeSwitch_changesColorSpace() {
        composeRule.waitForIdle()

        val initialColor = composeRule
            .onNodeWithTag("app")
            .captureToImage()
            .colorSpace

        composeRule.activity.runOnUiThread {
            runBlocking {
                composeRule.activity.appComponent
                    .settingsRepository
                    .setDarkModeEnabled(true)
            }
        }

        composeRule.waitForIdle()

        val newColor = composeRule
            .onNodeWithTag("app")
            .captureToImage()
            .colorSpace

        assertNotEquals(initialColor, newColor)
    }

    @Test
    fun themeSwitch_changesColorSpace() {
        val initialColor = composeRule
            .onNodeWithTag("app")
            .captureToImage()
            .colorSpace

        composeRule.activity.runOnUiThread {
            runBlocking {
                composeRule.activity.appComponent
                    .settingsRepository
                    .setThemeState(ThemeState.OCEAN)
            }
        }

        composeRule.waitForIdle()

        val newColor = composeRule
            .onNodeWithTag("app")
            .captureToImage()
            .colorSpace

        assertNotEquals(initialColor, newColor)
    }
}
