package ru.ttb220.demo.ui

import androidx.navigation.NavController
import ru.ttb220.ui.model.TopLevelDestination

class AppState(
    private val navController: NavController,
) {
    val currentTopLevelDestination: TopLevelDestination? =
        navController.currentDestination?.route?.let { TopLevelDestination.valueOf(it) }
}