package ru.ttb220.pin.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.ttb220.pin.presentation.viewmodel.PinViewModel
import ru.ttb220.presentation.model.R

@Composable
fun SetupPinScreen(
    viewModel: PinViewModel,
    modifier: Modifier = Modifier,
    onSuccess: () -> Unit,
) {
    SetupPinScreen(
        modifier = modifier,
        setupPinCode = viewModel::setupPinCode,
        onSuccess = onSuccess
    )
}

@Composable
fun SetupPinScreen(
    modifier: Modifier = Modifier,
    setupPinCode: (Int) -> Unit = {},
    onSuccess: () -> Unit = {},
) {
    var firstPin: Int? by remember { mutableStateOf(null) }

    if (firstPin != null) {
        PinInputScreen(
            title = R.string.repeat_password,
            onSuccess = onSuccess,
        ) { pin, onSuccess ->
            if (pin == firstPin) {
                setupPinCode(pin)
                onSuccess()
                return@PinInputScreen
            }
            firstPin = null
        }
        return
    }

    PinInputScreen(
        title = R.string.enter_password
    ) { pin, _ ->
        firstPin = pin
    }
}

@Preview
@Composable
private fun SPSPreview() {
    SetupPinScreen { }
}