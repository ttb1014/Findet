package ru.ttb220.income.presentation.component

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.designsystem.component.DatePickerDialog
import ru.ttb220.designsystem.component.ErrorBox
import ru.ttb220.designsystem.component.LoadingWheel
import ru.ttb220.designsystem.component.ThreeComponentListItem
import ru.ttb220.income.presentation.model.EditIncomeIntent
import ru.ttb220.income.presentation.model.EditIncomeState
import ru.ttb220.income.presentation.model.IncomeScreenData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.util.CurrencyVisualTransformation

@Composable
fun EditIncomeForm(
    state: EditIncomeState,
    modifier: Modifier = Modifier,
    onIntent: (EditIncomeIntent) -> Unit = {},
    // external callbacks
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
) {
    when (state) {
        EditIncomeState.Loading ->
            Box(
                modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingWheel(Modifier.size(160.dp))
            }

        is EditIncomeState.ErrorResource ->
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ErrorBox(
                    state.messageId
                )
            }

        is EditIncomeState.Content ->
            EditIncomeContent(
                state.data,
                modifier = modifier,
                onIntent = { onIntent(it) },
                onAccountSelectorLaunch = onAccountSelectorLaunch,
                onCategorySelectorLaunch = onCategorySelectorLaunch
            )
    }
}

@Composable
private fun EditIncomeContent(
    incomeScreenData: IncomeScreenData,
    modifier: Modifier = Modifier,
    onIntent: (EditIncomeIntent) -> Unit = {},
    // external callbacks
    onAccountSelectorLaunch: () -> Unit = {},
    onCategorySelectorLaunch: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        AccountSelector(
            accountName = incomeScreenData.accountName,
            onClick = onAccountSelectorLaunch
        )
        CategorySelector(
            categoryName = incomeScreenData.categoryName,
            onClick = onCategorySelectorLaunch
        )
        AmountSelector(
            amount = incomeScreenData.amount,
            currencySymbol = incomeScreenData.currencySymbol,
            onTextEdited = { onIntent(EditIncomeIntent.ChangeAmount(it)) }
        )
        DateSelector(
            date = incomeScreenData.date,
            onClick = { onIntent(EditIncomeIntent.ShowDatePicker) }
        )
        TimeSelector(
            time = incomeScreenData.time,
            onTextEdited = { onIntent(EditIncomeIntent.ChangeTime(it)) }
        )
        CommentField(
            comment = incomeScreenData.comment ?: "",
            onTextEdited = { onIntent(EditIncomeIntent.ChangeComment(it)) }
        )
    }

    if (incomeScreenData.isDatePickerShown) {
        DatePickerDialog(
            onDismiss = {
                onIntent(EditIncomeIntent.HideDatePicker)
            },
            onDateSelected = {
                onIntent(EditIncomeIntent.ChangeDate(it))
            }
        )
    }
}

@Composable
private fun TextResource(
    @StringRes id: Int,
) {
    Text(
        text = stringResource(
            id = id
        ),
        color = MaterialTheme.colorScheme.onSurface,
        softWrap = false,
        maxLines = 1,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
private fun Selector(
    text: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurface,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(Modifier.width(16.dp))
        Icon(
            painterResource(R.drawable.more_right),
            null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun AccountSelector(
    accountName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ThreeComponentListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            TextResource(R.string.account_selector_title)
        },
        trailingContent = @Composable {
            Selector(
                accountName,
                onClick = onClick
            )
        },
        centerContent = @Composable { modifier ->
            Spacer(modifier)
        },
    )
}

@Composable
private fun CategorySelector(
    categoryName: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ThreeComponentListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            TextResource(R.string.category_selector_title)
        },
        trailingContent = @Composable {
            Selector(
                categoryName,
                onClick = onClick
            )
        },
        centerContent = @Composable { modifier ->
            Spacer(modifier)
        },
    )
}

@Composable
private fun AmountSelector(
    amount: String,
    currencySymbol: String,
    modifier: Modifier = Modifier,
    onTextEdited: (String) -> Unit = {},
) {
    ThreeComponentListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            TextResource(R.string.amount_selector_title)
        },
        trailingContent = @Composable {
            BasicTextField(
                value = amount,
                onValueChange = onTextEdited,
                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End),
                keyboardOptions = digitKeyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        defaultKeyboardAction(ImeAction.Done)
                        // TODO: remove focus from TF
                    }
                ),
                singleLine = true,
                visualTransformation = CurrencyVisualTransformation(currencySymbol)
            )
        },
        centerContent = @Composable { modifier ->
            Spacer(modifier)
        },
    )
}

private val digitKeyboardOptions = KeyboardOptions(
    keyboardType = KeyboardType.Number,
    imeAction = ImeAction.Done
)

@Composable
private fun DateSelector(
    date: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    ThreeComponentListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            TextResource(R.string.date_selector_title)
        },
        trailingContent = @Composable {
            Text(
                text = date,
                color = MaterialTheme.colorScheme.onSurface,
                softWrap = false,
                maxLines = 1,
                modifier = Modifier.clickable(onClick = onClick),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        centerContent = @Composable { modifier ->
            Spacer(modifier)
        },
    )
}

@Composable
private fun TimeSelector(
    time: String,
    modifier: Modifier = Modifier,
    onTextEdited: (String) -> Unit = {}
) {
    ThreeComponentListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            TextResource(R.string.time_selector_title)
        },
        trailingContent = @Composable {
            BasicTextField(
                value = time,
                onValueChange = onTextEdited,
                textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End),
                keyboardOptions = digitKeyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        defaultKeyboardAction(ImeAction.Done)
                        // TODO: remove focus from TF
                    }
                ),
                singleLine = true,
            )
        },
        centerContent = @Composable { modifier ->
            Spacer(modifier)
        },
    )
}

@Composable
private fun CommentField(
    comment: String,
    modifier: Modifier = Modifier,
    onTextEdited: (String) -> Unit = {}
) {
    ThreeComponentListItem(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            BasicTextField(
                value = comment,
                onValueChange = onTextEdited,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardActions = KeyboardActions(
                    onDone = {
                        defaultKeyboardAction(ImeAction.Done)
                        // TODO: remove focus from TF
                    }
                ),
                singleLine = true,
            )
        },
        centerContent = @Composable { modifier ->
            Spacer(modifier)
        },
    )
}

@Preview
@Composable
private fun AddIncomeScreenPreview() {
    EditIncomeContent(
        incomeScreenData = IncomeScreenData(
            incomeId = 123,
            accountName = "Сбербанк",
            categoryName = "Ремонт",
            amount = "25 270",
            date = "25.02.2025",
            dateMillis = 0L,
            time = "23:41",
            comment = "Ремонт - фурнитура для дверей",
            currencySymbol = "₽",
            accountId = 54,
            categoryId = 12,
            isDatePickerShown = false
        )
    )
}