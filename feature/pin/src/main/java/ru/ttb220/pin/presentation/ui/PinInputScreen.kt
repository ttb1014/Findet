package ru.ttb220.pin.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.pin.presentation.viewmodel.PinViewModel
import ru.ttb220.presentation.model.R

@Composable
fun PinInputScreen(
    viewModel: PinViewModel,
    modifier: Modifier = Modifier,
    onSuccess: () -> Unit = {}
) {
    PinInputScreen(
        modifier = modifier,
        onSuccess = onSuccess,
        onPinEntered = viewModel::onPinEntered
    )
}

@Composable
fun PinInputScreen(
    modifier: Modifier = Modifier,
    @StringRes title: Int = R.string.enter_password,
    onSuccess: () -> Unit = {},
    onPinEntered: (Int, () -> Unit) -> Unit = { _, _ -> },
) {
    var pin by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(title),
            softWrap = false,
            maxLines = 1,
            minLines = 1,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(Modifier.height(16.dp))

        BasicTextField(
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
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = PasswordVisualTransformation(),
        )
    }
}

@Preview
@Composable
private fun PISPreview() {
    PinInputScreen()
}