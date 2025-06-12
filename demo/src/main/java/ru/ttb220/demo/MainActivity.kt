package ru.ttb220.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ru.ttb220.demo.navigation.Destination
import ru.ttb220.demo.ui.FindetApp
import ru.ttb220.demo.ui.rememberAppState
import ru.ttb220.ui.theme.FindetTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !mainViewModel.isReady.value
            }
        }

        enableEdgeToEdge()

        setContent {
            FindetTheme {
                val appState = rememberAppState()
                FindetApp(
                    appState,
                    Destination.ACCOUNT.name
                )
            }
        }
    }
}