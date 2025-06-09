package ru.ttb220.demo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

class AppState(
    private val navController: NavController,
) {
    val currentRoute: String?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination?.route
}