package ru.ttb220.pin.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import ru.ttb220.presentation.model.R

@Composable
fun PinInputScreen(
    @StringRes title: Int = R.string.enter_password,
    onSuccess: () -> Unit = {},
    onPinEntered: (Int, () -> Unit) -> Unit = { _, _ -> },
) {
    var pin by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(title),
            softWrap = false,
            maxLines = 1,
            minLines = 1,
            style = MaterialTheme.typography.bodyLarge
        )

        TextField(
            value = pin,
            onValueChange = {
                if (it.length <= 4 && it.all { c -> c.isDigit() }) {
                    pin = it
                    if (it.length == 4)
                        onPinEntered(
                            it.toInt(),
                            onSuccess
                        )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("PIN") }
        )
    }
}

@Preview
@Composable
private fun PISPreview() {
    PinInputScreen()
}