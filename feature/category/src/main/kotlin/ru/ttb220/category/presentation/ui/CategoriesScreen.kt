package ru.ttb220.category.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.category.presentation.viewmodel.CategoriesViewModel
import ru.ttb220.category.presentation.mock.mockCategoriesScreenContent
import ru.ttb220.category.presentation.model.CategoriesScreenState
import ru.ttb220.designsystem.ColumnListItem
import ru.ttb220.designsystem.DynamicIconResource
import ru.ttb220.designsystem.ErrorBox
import ru.ttb220.designsystem.LoadingWheel
import ru.ttb220.presentation.model.CategoryData
import ru.ttb220.presentation.model.R
import ru.ttb220.presentation.model.screen.CategoriesScreenData

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    viewModel: CategoriesViewModel
) {
    val categoriesScreenState by viewModel.categoriesScreenState.collectAsStateWithLifecycle()

    when (categoriesScreenState) {
        is CategoriesScreenState.Error -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                (categoriesScreenState as CategoriesScreenState.Error).message
            )
        }

        is CategoriesScreenState.ErrorResource -> Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ErrorBox(
                (categoriesScreenState as CategoriesScreenState.ErrorResource).messageId
            )
        }

        is CategoriesScreenState.Loaded -> {
            CategoriesScreenContent(
                categoriesScreenData = (categoriesScreenState as CategoriesScreenState.Loaded).data,
                modifier = modifier,
                onSearch = viewModel::onSearch
            )
        }

        CategoriesScreenState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LoadingWheel(Modifier.size(160.dp))
        }
    }
}

@Composable
fun CategoriesScreenContent(
    categoriesScreenData: CategoriesScreenData,
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        SearchBar(
            onSearch = onSearch
        )
        LazyColumn {
            items(count = categoriesScreenData.data.size) { index ->
                val item = categoriesScreenData.data[index]
                CategoryItem(
                    categoryData = item,
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var query by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    BackHandler(enabled = isFocused) {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                .height(56.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = query,
                onValueChange = {
                    query = it
                    onSearch(query)
                },
                modifier = Modifier
                    .padding(start = 4.dp)
                    .weight(1f)
                    .onFocusChanged { state ->
                        isFocused = state.isFocused
                    },
                textStyle = MaterialTheme.typography.bodyLarge,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                    }
                ),
                singleLine = true,
                decorationBox = { innerTextField ->
                    if (query.isEmpty() && !isFocused) {
                        Text(
                            text = stringResource(id = R.string.find_category),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                        )
                    }
                    innerTextField()
                }
            )
            Icon(
                painter = painterResource(R.drawable.search),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}

@Composable
fun CategoryItem(
    categoryData: CategoryData,
    modifier: Modifier = Modifier,
    shouldShowLeadingDivider: Boolean = false,
) {
    ColumnListItem(
        title = categoryData.name,
        modifier = modifier.height(70.dp),
        dynamicIconResource = categoryData.emojiData?.let {
            DynamicIconResource.EmojiIconResource(
                it
            )
        } ?: DynamicIconResource.TextIconResource(
            categoryData.name
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
    CategoriesScreenContent(
        mockCategoriesScreenContent
    )
}