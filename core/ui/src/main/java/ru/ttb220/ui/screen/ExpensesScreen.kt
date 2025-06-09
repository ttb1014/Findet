package ru.ttb220.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.ttb220.mock.mockExpenseResources
import ru.ttb220.model.expense.ExpenseResource
import ru.ttb220.ui.R
import ru.ttb220.ui.component.ColumnListItem
import ru.ttb220.ui.component.LeadingIcon
import ru.ttb220.ui.theme.Roboto

private val DEFAULT_ICON_TINT = Color(0xFF3C3C43).copy(alpha = 0.3f)
private val DEFAULT_TOTAL_AMOUNT_HEADER_FILL = Color(0xFFD4FAE6)

// TODO: Стоит переделать на LazyColumn.
@Composable
fun ExpensesScreen(
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
private fun TotalAmountHeader(
    expensesTotal: String,
    modifier: Modifier = Modifier,
) {
    ColumnListItem(
        title = "Всего",
        trailingText = expensesTotal,
        modifier = modifier.height(56.dp),
        background = DEFAULT_TOTAL_AMOUNT_HEADER_FILL
    )
}

@Composable
private fun ExpenseColumnItem(
    expenseResource: ExpenseResource,
    modifier: Modifier = Modifier,
) {
    val leadingIcon = expenseResource.emojiId
        ?.let { LeadingIcon.Emoji(emojiId = it) }
        ?: LeadingIcon.Letters(expenseResource.name
            .split(" ")
            .map { it[0] }
            .take(2)
            .joinToString("")
            .uppercase()
        )

    ColumnListItem(
        leadingIcon = leadingIcon,
        title = expenseResource.name,
        trailingText = expenseResource.amount,
        trailingIcon = R.drawable.more_right,
        modifier = Modifier.height(70.dp),
        description = expenseResource.shortDescription,
        shouldShowTrailingDivider = true
    )
}

@Composable
private fun LeadingIcon(
    @DrawableRes emojiId: Int?,
    expenseName: String,
    modifier: Modifier = Modifier,
) {
    emojiId?.let {
        EmojiIcon(
            emojiId = emojiId,
            modifier = modifier
        )
    } ?: TextIcon(
        letters = expenseName,
        modifier = Modifier
    )
}

@Composable
private fun TextIcon(
    letters: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(24.dp)
            .background(Color(0xFFD4FAE6), CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = letters,
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

@Preview
@Composable
private fun ExpensesListPreview() {
    ExpensesScreen(
        expenses = mockExpenseResources,
        expensesTotal = "436 558 ₽",
        modifier = Modifier,
    )
}