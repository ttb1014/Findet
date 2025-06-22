package ru.ttb220.presentation.model.screen

import androidx.compose.runtime.Immutable
import ru.ttb220.presentation.model.CategoryData

@Immutable
data class CategoriesScreenData(
    val data: List<CategoryData>
)
