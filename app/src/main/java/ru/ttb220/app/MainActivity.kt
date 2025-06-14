package ru.ttb220.app

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ru.ttb220.app.navigation.Destination
import ru.ttb220.app.ui.FindetApp
import ru.ttb220.app.ui.rememberAppState
import ru.ttb220.ui.theme.FindetTheme

class MainActivity : ComponentActivity() {

    // Управляет состоянием сплеш-скрина
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем сплеш скрин
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !mainViewModel.isReady.value
            }

            // Анимация на выходе из сплеша
            setOnExitAnimationListener { screen ->
                val scaleX = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_X,
                    0.4f,
                    0.0f
                )
                val scaleY = ObjectAnimator.ofFloat(
                    screen.iconView,
                    View.SCALE_Y,
                    0.4f,
                    0.0f
                )
                scaleX.duration = 500L
                scaleY.duration = 500L

                scaleX.start()
                scaleY.start()
                scaleX.doOnEnd { screen.remove() }
            }
        }

        enableEdgeToEdge()

        setContent {
            FindetTheme {
                // Сохраняем состояние между рекомпозициями
                val appState = rememberAppState()
                FindetApp(
                    appState,
                    Destination.EXPENSES.name
                )
            }
        }
    }
}