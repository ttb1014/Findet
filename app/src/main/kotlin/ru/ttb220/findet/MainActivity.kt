package ru.ttb220.findet

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.ttb220.designsystem.theme.FindetTheme
import ru.ttb220.findet.presentation.ui.FindetApp
import ru.ttb220.findet.presentation.ui.rememberAppState
import ru.ttb220.findet.presentation.viewmodel.MainViewModel
import ru.ttb220.findet.util.ProvideLocalizedContext
import ru.ttb220.findet.util.appComponent
import ru.ttb220.model.SupportedLanguage
import ru.ttb220.model.ThemeState
import javax.inject.Inject
import kotlinx.coroutines.flow.onEach

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
                    screen.iconView, View.SCALE_X, 0.4f, 0.0f
                )
                val scaleY = ObjectAnimator.ofFloat(
                    screen.iconView, View.SCALE_Y, 0.4f, 0.0f
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
            val themeState by appComponent.settingsRepository.getThemeStateFlow()
                .collectAsStateWithLifecycle(
                    ThemeState.ORIGINAL
                )
            val isDarkModeEnabled by appComponent.settingsRepository.isDarkModeEnabled()
                .collectAsStateWithLifecycle(
                    isSystemInDarkTheme()
                )
            val activeLanguage by mainViewModel
                .activeLanguageFlow()
                .collectAsStateWithLifecycle(
                    SupportedLanguage.RUSSIAN
                )

            ProvideLocalizedContext(activeLanguage.locale) {
                FindetTheme(
                    darkTheme = isDarkModeEnabled,
                    themeState = themeState
                ) {
                    val activeAccountId by mainViewModel.activeAccountId.collectAsStateWithLifecycle()

                    val appState = rememberAppState(
                        activeAccountId = activeAccountId,
                        networkMonitor = appComponent.networkMonitor,
                        syncManager = appComponent.syncManager,
                        settingsRepository = appComponent.settingsRepository,
                        timeZone = appComponent.timeZone
                    )
                    FindetApp(
                        appState,
                        modifier = Modifier.testTag("app")
                    )
                }
            }
        }
    }
}