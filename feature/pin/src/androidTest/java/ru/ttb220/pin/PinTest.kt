package ru.ttb220.pin

import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performTextInput
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import ru.ttb220.pin.presentation.ui.PinInputScreen

class PinTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pinInput_onSuccessCalled_whenPinCorrect() {
        var successCalled = false
        var enteredPin: Int? = null

        composeTestRule.setContent {
            PinInputScreen(
                onPinEntered = { pin, onSuccess ->
                    enteredPin = pin
                    if (pin == 1234) {
                        onSuccess()
                    }
                },
                onSuccess = {
                    successCalled = true
                }
            )
        }

        composeTestRule.onNode(hasSetTextAction()).performTextInput("1234")

        assertEquals(1234, enteredPin)
        assertTrue(successCalled)
    }

    @Test
    fun pinInput_onSuccessNotCalled_whenPinIncorrect() {
        var successCalled = false
        var enteredPin: Int? = null

        composeTestRule.setContent {
            PinInputScreen(
                onPinEntered = { pin, onSuccess ->
                    enteredPin = pin
                    if (pin == 1234) {
                        onSuccess()
                    }
                },
                onSuccess = {
                    successCalled = true
                }
            )
        }

        composeTestRule.onNode(hasSetTextAction()).performTextInput("4321")

        assertEquals(4321, enteredPin)
        assertFalse(successCalled)
    }

    @Test
    fun pinInput_onSuccessNotCalled_whenPinIncomplete() {
        var successCalled = false
        var enteredPin: Int? = null

        composeTestRule.setContent {
            PinInputScreen(
                onPinEntered = { pin, onSuccess ->
                    enteredPin = pin
                    if (pin == 1234) {
                        onSuccess()
                    }
                },
                onSuccess = {
                    successCalled = true
                }
            )
        }

        composeTestRule.onNode(hasSetTextAction()).performTextInput("12")

        assertNull(enteredPin)
        assertFalse(successCalled)
    }
}