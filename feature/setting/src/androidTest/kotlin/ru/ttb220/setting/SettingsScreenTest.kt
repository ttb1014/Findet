package ru.ttb220.setting

import androidx.compose.material3.Switch
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import ru.ttb220.presentation.model.screen.SettingsScreenData
import ru.ttb220.setting.presentation.ui.SettingsScreen

class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun themeSwitch_toggle_changesValue() {
        var isDarkMode = false

        composeTestRule.setContent {
            Switch(
                checked = isDarkMode,
                onCheckedChange = { isDarkMode = it },
                modifier = Modifier.testTag("theme_switch")
            )
        }

        composeTestRule.onNodeWithTag("theme_switch")
            .assertIsOff()
            .performClick()
            .assertIsOn()

        assert(isDarkMode)
    }

    @Test
    fun languageDropdownAppearsWhenClicked() {
        val fakeData = SettingsScreenData(
            isDarkThemeEnabled = false,
        )

        composeTestRule.setContent {
            SettingsScreen(
                settingsScreenData = fakeData,
                onLanguageSelected = {}
            )
        }

        composeTestRule.onNodeWithTag("language_dropdown")
            .assertDoesNotExist()

        composeTestRule.onNodeWithTag("language_item")
            .performClick()

        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithTag("language_dropdown").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("language_dropdown")
            .assertIsDisplayed()
    }
}
