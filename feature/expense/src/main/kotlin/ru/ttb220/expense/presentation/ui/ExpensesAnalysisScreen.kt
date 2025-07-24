package ru.ttb220.expense.presentation.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.designsystem.component.DatePickerDialog
import ru.ttb220.designsystem.component.DynamicIcon
import ru.ttb220.designsystem.component.DynamicIconResource
import ru.ttb220.designsystem.component.ErrorBox
import ru.ttb220.designsystem.component.LoadingWheel
import ru.ttb220.designsystem.component.MonthChip
import ru.ttb220.designsystem.component.ThreeComponentListItem
import ru.ttb220.expense.presentation.model.ExpenseAnalysisItemData
import ru.ttb220.expense.presentation.model.ExpensesAnalysisScreenState
import ru.ttb220.expense.presentation.viewmodel.ExpensesAnalysisScreenViewModel
import ru.ttb220.presentation.model.R

@Composable
fun ExpensesAnalysisScreen(
    viewModel: ExpensesAnalysisScreenViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ExpensesAnalysisScreen(
        state,
        modifier,
        viewModel::setStartDate,
        viewModel::setEndDate,
    )
}

@Composable
fun ExpensesAnalysisScreen(
    expenseAnalysisScreenState: ExpensesAnalysisScreenState,
    modifier: Modifier = Modifier,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {},
) {
    when (expenseAnalysisScreenState) {
        is ExpensesAnalysisScreenState.ErrorResource -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                messageId = expenseAnalysisScreenState.messageId,
                modifier = Modifier,
            )
        }

        ExpensesAnalysisScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }

        is ExpensesAnalysisScreenState.Content -> {
            with(expenseAnalysisScreenState) {
                ExpensesAnalysisScreen(
                    beginMonthWithYear,
                    endMonthWithYear,
                    totalAmount,
                    items,
                    modifier = modifier,
                    onStartDateSelected = onStartDateSelected,
                    onEndDateSelected = onEndDateSelected
                )
            }
        }

        is ExpensesAnalysisScreenState.Empty -> with(expenseAnalysisScreenState) {
            ExpensesAnalysisScreen(
                beginMonthWithYear,
                endMonthWithYear,
                totalAmount,
                modifier = modifier,
                onStartDateSelected = onStartDateSelected,
                onEndDateSelected = onEndDateSelected
            )
        }
    }
}

@Composable
private fun EmptyPlaceholder() =
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ErrorBox(
            messageId = R.string.error_empty,
            modifier = Modifier,
        )
    }

@Composable
private fun PeriodColumnListItem(
    @StringRes title: Int,
    monthWithYear: String,
    modifier: Modifier = Modifier
) {
    ThreeComponentListItem(
        modifier = modifier.height(56.dp),
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            Text(
                text = stringResource(title),
                color = MaterialTheme.colorScheme.onSurface,
                softWrap = false,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = @Composable {
            MonthChip(
                monthWithYear = monthWithYear,
            )
        },
        centerContent = @Composable {
            Spacer(modifier = it)
        }
    )
}

@Composable
private fun TotalAmountItem(
    amount: String,
    modifier: Modifier = Modifier
) {
    ThreeComponentListItem(
        modifier = modifier.height(56.dp),
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            Text(
                text = stringResource(R.string.total),
                color = MaterialTheme.colorScheme.onSurface,
                softWrap = false,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailingContent = @Composable {
            Text(
                text = amount,
                color = MaterialTheme.colorScheme.onSurface,
                softWrap = false,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        centerContent = @Composable {
            Spacer(modifier = it)
        }
    )
}

@Composable
fun ExpenseAnalysisItem(
    leadingIcon: DynamicIconResource,
    expenseName: String,
    expensePercentage: String,
    expenseAmount: String,
    modifier: Modifier = Modifier,
    shouldShowLeadingDivider: Boolean = false,
    expenseDescription: String? = null,
) {
    ThreeComponentListItem(
        modifier = modifier.height(68.dp),
        shouldShowLeadingDivider = shouldShowLeadingDivider,
        shouldShowTrailingDivider = true,
        leadingContent = @Composable {
            DynamicIcon(
                leadingIcon
            )
        },
        trailingContent = @Composable {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = AbsoluteAlignment.Right
                ) {
                    Text(
                        text = expensePercentage,
                        color = MaterialTheme.colorScheme.onSurface,
                        softWrap = false,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Text(
                        text = expenseAmount,
                        color = MaterialTheme.colorScheme.onSurface,
                        softWrap = false,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Spacer(Modifier.width(16.dp))
                Icon(
                    painterResource(R.drawable.more_right),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null,
                )
            }
        },
        centerContent = @Composable {
            Column(
                modifier = it
            ) {
                Text(
                    text = expenseName,
                    color = MaterialTheme.colorScheme.onSurface,
                    softWrap = false,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
                expenseDescription?.let {
                    Text(
                        text = expenseDescription,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        softWrap = false,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        },
    )
}

@Preview
@Composable
private fun ExpenseAnalysisItemPreview() {
    ExpenseAnalysisItem(
        leadingIcon = DynamicIconResource.TextIconResource("РК"),
        expenseName = "Ремонт квартиры",
        expenseDescription = "Ремонт - фурнитура для дверей",
        expensePercentage = "80%",
        expenseAmount = "20 000 ₽"
    )
}

@Composable
fun ExpensesAnalysisScreen(
    beginMonthWithYear: String,
    endMonthWithYear: String,
    totalAmount: String,
    items: List<ExpenseAnalysisItemData>,
    modifier: Modifier = Modifier,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {}
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        PeriodColumnListItem(
            R.string.begin,
            beginMonthWithYear,
            Modifier.clickable {
                showStartDatePicker = true
                showEndDatePicker = false
            }
        )
        PeriodColumnListItem(
            R.string.period_end,
            endMonthWithYear,
            Modifier.clickable {
                showStartDatePicker = false
                showEndDatePicker = true
            }
        )
        TotalAmountItem(
            totalAmount,
        )
        Spacer(Modifier.height(17.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, bottom = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(ru.ttb220.expense.R.drawable.mock_circle_diagram),
                null,
                contentScale = ContentScale.None
            )
        }
        LazyColumn {
            items(items.size) { index ->
                val item = items[index]
                ExpenseAnalysisItem(
                    leadingIcon = item.leadingIcon,
                    expenseName = item.expenseName,
                    expenseDescription = item.expenseDescription,
                    expensePercentage = item.expensePercentage,
                    expenseAmount = item.expenseAmount,
                    shouldShowLeadingDivider = index == 0
                )
            }
        }
    }

    if (showStartDatePicker) {
        DatePickerDialog(
            onDismiss = { showStartDatePicker = false },
            onDateSelected = {
                showStartDatePicker = false
                onStartDateSelected(it)
            }
        )
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDismiss = { showEndDatePicker = false },
            onDateSelected = {
                showEndDatePicker = false
                onEndDateSelected(it)
            }
        )
    }
}

@Composable
fun ExpensesAnalysisScreen(
    beginMonthWithYear: String,
    endMonthWithYear: String,
    totalAmount: String,
    modifier: Modifier = Modifier,
    onStartDateSelected: (Long?) -> Unit = {},
    onEndDateSelected: (Long?) -> Unit = {}
) {
    var showStartDatePicker by remember { mutableStateOf(false) }
    var showEndDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        PeriodColumnListItem(
            R.string.begin,
            beginMonthWithYear,
            Modifier.clickable {
                showStartDatePicker = true
                showEndDatePicker = false
            }
        )
        PeriodColumnListItem(
            R.string.period_end,
            endMonthWithYear,
            Modifier.clickable {
                showStartDatePicker = false
                showEndDatePicker = true
            }
        )
        TotalAmountItem(
            totalAmount,
        )
        Spacer(Modifier.height(17.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 36.dp, bottom = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(ru.ttb220.expense.R.drawable.mock_circle_diagram),
                null,
                contentScale = ContentScale.None
            )
        }
        EmptyPlaceholder()
    }

    if (showStartDatePicker) {
        DatePickerDialog(
            onDismiss = { showStartDatePicker = false },
            onDateSelected = {
                showStartDatePicker = false
                onStartDateSelected(it)
            }
        )
    }

    if (showEndDatePicker) {
        DatePickerDialog(
            onDismiss = { showEndDatePicker = false },
            onDateSelected = {
                showEndDatePicker = false
                onEndDateSelected(it)
            }
        )
    }
}

@Preview
@Composable
private fun ExpensesAnalysisScreenPreview() {
    ExpensesAnalysisScreen(
        beginMonthWithYear = "февраль 2025",
        endMonthWithYear = "март 2025",
        totalAmount = "20 000 ₽",
        items = listOf(
            ExpenseAnalysisItemData(
                leadingIcon = DynamicIconResource.TextIconResource("РК"),
                expenseName = "Ремонт квартиры",
                expenseDescription = "Ремонт - фурнитура для дверей",
                expensePercentage = "80%",
                expenseAmount = "20 000 ₽"
            )
        ),
    )
}