package ru.ttb220.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import ru.ttb220.demo.ui.FindetApp
import ru.ttb220.demo.ui.navigation.Destination
import ru.ttb220.ui.theme.FindetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FindetTheme {
                val navHostController = rememberNavController()
                FindetApp(
                    navHostController,
                    Destination.EXPENSES.name
                )
            }
        }
    }
}