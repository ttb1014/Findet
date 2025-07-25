package ru.ttb220.account.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.ttb220.account.presentation.viewmodel.AddAccountViewModel
import ru.ttb220.designsystem.component.ThreeComponentListItem
import ru.ttb220.presentation.model.R

@Composable
fun AddAccountScreenContent(
    modifier: Modifier = Modifier,
    addAccountViewModel: AddAccountViewModel
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MyInputItem(
            tag = R.string.name,
            text = addAccountViewModel.name,
            onInput = {
                addAccountViewModel.name = it
            },
            Modifier.height(56.dp)
        )

        MyInputItem(
            tag = R.string.balance,
            text = addAccountViewModel.balance,
            onInput = {
                addAccountViewModel.balance = it
            },
            Modifier.height(56.dp)
        )

        MyInputItem(
            tag = R.string.currency,
            text = addAccountViewModel.currency,
            onInput = {
                addAccountViewModel.currency = it
            },
            Modifier.height(56.dp)
        )
    }
}

@Composable
fun ColumnScope.MyInputItem(
    @StringRes tag: Int,
    text: String,
    onInput: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    ThreeComponentListItem(
        shouldShowTrailingDivider = true,
        leadingContent = {
            Text(
                text = stringResource(tag),
                modifier = Modifier.width(80.dp),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = {
            BasicTextField(
                value = text,
                onValueChange = { onInput(it) },
                textStyle = MaterialTheme.typography.bodyLarge,
                singleLine = true,
                maxLines = 1,
                modifier = Modifier.weight(1f)
            )
        },
        modifier = modifier
    )
}