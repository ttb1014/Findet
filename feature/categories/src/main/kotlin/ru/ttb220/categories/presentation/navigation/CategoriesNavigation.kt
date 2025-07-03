package ru.ttb220.categories.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import ru.ttb220.categories.presentation.ui.CategoriesScreen
import ru.ttb220.mock.mockCategoriesScreenContent

const val TOP_LEVEL_CATEGORIES_ROUTE = "categories"

fun NavController.navigateToCategories(
    navOptions: NavOptions? = null
) = navigate(TOP_LEVEL_CATEGORIES_ROUTE, navOptions)

fun NavGraphBuilder.categoriesScreen() {
    composable(
        route = TOP_LEVEL_CATEGORIES_ROUTE,
    ) {
        CategoriesScreen(
            mockCategoriesScreenContent
        )
    }
}