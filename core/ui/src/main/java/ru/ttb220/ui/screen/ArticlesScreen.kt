package ru.ttb220.ui.screen

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.ttb220.mock.mockArticleScreenResource
import ru.ttb220.model.ArticleResource
import ru.ttb220.model.ArticlesScreenResource
import ru.ttb220.ui.R
import ru.ttb220.ui.component.ColumnListItem
import ru.ttb220.ui.component.LeadingIcon

@Composable
fun ArticlesScreen(
    articlesScreenResource: ArticlesScreenResource,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        SearchBar()
        articlesScreenResource.forEachIndexed { index, item ->
            ArticleItem(
                articleResource = item,
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
            text = "Найти статью",
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
fun ArticleItem(
    articleResource: ArticleResource,
    modifier: Modifier = Modifier,
    shouldShowLeadingDivider: Boolean = false,
) {
    ColumnListItem(
        title = articleResource.name,
        modifier = modifier.height(70.dp),
        leadingIcon = articleResource.emojiId?.let {
            LeadingIcon.Emoji(
                it
            )
        } ?: LeadingIcon.Letters(
            articleResource.name
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
private fun ArticlesScreenPreview() {
    ArticlesScreen(
        articlesScreenResource = mockArticleScreenResource,
    )
}