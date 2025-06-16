package ru.ttb220.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockArticleScreenContent
import ru.ttb220.presentation.model.CategoryState
import ru.ttb220.presentation.model.screen.CategoriesScreenResource
import ru.ttb220.presentation.ui.R
import ru.ttb220.presentation.ui.component.ColumnListItem
import ru.ttb220.presentation.ui.component.DynamicIconResource

@Composable
fun CategoriesScreen(
    categoriesScreenResource: CategoriesScreenResource,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        SearchBar()
        categoriesScreenResource.forEachIndexed { index, item ->
            CategoryItem(
                categoryState = item,
                shouldShowLeadingDivider = index == 0
            )
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .height(56.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.find_category),
            modifier = Modifier
                .padding(start = 4.dp)
                .weight(1f),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            softWrap = false,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            painter = painterResource(R.drawable.search),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun CategoryItem(
    categoryState: CategoryState,
    modifier: Modifier = Modifier,
    shouldShowLeadingDivider: Boolean = false,
) {
    ColumnListItem(
        title = categoryState.name,
        modifier = modifier.height(70.dp),
        dynamicIconResource = categoryState.emoji?.let {
            DynamicIconResource.EmojiIconResource(
                it
            )
        } ?: DynamicIconResource.TextIconResource(
            categoryState.name
                .split(" ")
                .map { it[0] }
                .joinToString("")
                .uppercase()
        ),
        shouldShowLeadingDivider = shouldShowLeadingDivider,
        shouldShowTrailingDivider = true
    )
}

@Preview
@Composable
private fun CategoriesScreenPreview() {
    CategoriesScreen(
        categoriesScreenResource = mockArticleScreenContent,
    )
}