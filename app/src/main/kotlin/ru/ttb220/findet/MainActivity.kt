package ru.ttb220.findet

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.designsystem.theme.FindetTheme
import ru.ttb220.findet.di.appComponent
import ru.ttb220.findet.ui.FindetApp
import ru.ttb220.findet.ui.rememberAppState
import ru.ttb220.findet.ui.viewmodel.MainViewModel
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    // Управляет состоянием сплеш-скрина и активным аккаунтом
    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = appComponent.viewModelFactory.create(MainViewModel::class.java)

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
                val activeAccountId by mainViewModel.activeAccountId.collectAsStateWithLifecycle()

                val appState = rememberAppState(
                    activeAccountId = activeAccountId,
                    networkMonitor = appComponent.networkMonitor,
                    syncManager = appComponent.syncManager,
                    timeZone = appComponent.timeZone
                )
                FindetApp(
                    appState,
                )
            }
        }
    }
}