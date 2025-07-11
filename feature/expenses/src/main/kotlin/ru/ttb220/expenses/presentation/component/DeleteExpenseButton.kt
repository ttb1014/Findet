package ru.ttb220.expenses.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.ui.theme.KeyError

@Composable
fun DeleteExpenseButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
            .background(KeyError, RoundedCornerShape(100.dp))
            .clickable(onClick = onClick)
    ) {
        Text(
            text = stringResource(R.string.delete_expense),
            color = MaterialTheme.colorScheme.onPrimary,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview
@Composable
private fun DEBP() {
    DeleteExpenseButton { }
}