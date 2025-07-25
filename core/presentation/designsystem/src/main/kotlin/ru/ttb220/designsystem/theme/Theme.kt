package ru.ttb220.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ru.ttb220.model.ThemeState

@Composable
fun FindetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeState: ThemeState = ThemeState.ORIGINAL,
    content: @Composable () -> Unit
) {
    val colorScheme = when (darkTheme) {
        true -> when (themeState) {
            ThemeState.ORIGINAL -> OriginalDarkColorScheme
            ThemeState.GOLD -> GoldDarkColorScheme
            ThemeState.OCEAN -> OceanDarkColorScheme
            ThemeState.PURPLE -> PurpleDarkColorScheme
        }

        false -> when (themeState) {
            ThemeState.ORIGINAL -> OriginalLightColorScheme
            ThemeState.GOLD -> GoldLightColorScheme
            ThemeState.OCEAN -> OceanLightColorScheme
            ThemeState.PURPLE -> PurpleLightColorScheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}