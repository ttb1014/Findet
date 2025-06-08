package ru.ttb220.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ttb220.mock.mockExpenseResources
import ru.ttb220.model.ExpenseResource
import ru.ttb220.mock.mockTotalExpenses
import ru.ttb220.ui.R
import ru.ttb220.ui.theme.Roboto

val DEFAULT_TOTAL_AMOUNT_HEADER_FILL = Color(0xFFD4FAE6)

// TODO: На макете выглядит потемнее
val DEFAULT_ICON_TINT = Color(0xFF3C3C43).copy(alpha = 0.3f)

// TODO: Стоит переделать на LazyColumn.
@Composable
fun ExpensesList(
    expenses: List<ExpenseResource>,
    expensesTotal: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        TotalAmountHeader(expensesTotal)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            expenses.forEach {
                ExpenseColumnItem(it)
            }
        }
    }
}

@Composable
fun ExpenseColumnItem(
    expense: ExpenseResource,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .height(70.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                // TODO: заменить на fillMaxSize() отрисовав перед этим все нужные divider-ы
                .height(69.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            EmojiOrTextIcon(
                emojiId = expense.emojiId,
                expenseName = expense.name,
                modifier = Modifier
            )
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = expense.name,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onSurface,
                    softWrap = false,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
                expense.shortDescription?.let {
                    Text(
                        text = it,
                        modifier = Modifier,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        softWrap = false,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(Modifier.width(16.dp))
            Text(
                text = expense.amount,
                modifier = Modifier,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.End,
                softWrap = false,
                maxLines = 1,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.width(16.dp))
            Icon(
                painterResource(R.drawable.more_right),
                null,
                tint = DEFAULT_ICON_TINT
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
        )
    }
}

@Composable
fun EmojiOrTextIcon(
    @DrawableRes emojiId: Int?,
    expenseName: String,
    modifier: Modifier = Modifier,
) {
    emojiId?.let {
        EmojiIcon(it)
    } ?: TextIcon(expenseName)
}

@Composable
fun TextIcon(
    expenseName: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(Color(0xFFD4FAE6), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = expenseName.split(" ").map { it[0] }.take(2).joinToString("").uppercase(),
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 10.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Medium,
            fontFamily = Roboto,
            letterSpacing = 0.sp,
            lineHeight = 22.sp,
            softWrap = false,
            maxLines = 1,
        )
    }
}

@Composable
private fun EmojiIcon(
    @DrawableRes emojiId: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(emojiId),
        contentDescription = null,
        modifier = modifier.size(24.dp),
        alignment = Alignment.Center
    )
}

@Composable
private fun TotalAmountHeader(
    expensesTotal: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(DEFAULT_TOTAL_AMOUNT_HEADER_FILL)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Всего",
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Left,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = expensesTotal,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Left,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun TotalAmountHeaderPreview() {
    TotalAmountHeader(mockTotalExpenses)
}

@Preview
@Composable
private fun ExpensesListPreview() {
    ExpensesList(
        expenses = mockExpenseResources,
        expensesTotal = "436 558 ₽",
        modifier = Modifier,
    )
}