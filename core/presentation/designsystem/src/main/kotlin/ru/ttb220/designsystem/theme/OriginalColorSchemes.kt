package ru.ttb220.designsystem.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

private val primaryLight = Color(0xFF2AE881)
private val onPrimaryLight = Color(0xFF000000)
private val secondaryLight = Color(0xFFFF5F00)
private val primaryContainerLight = Color(0xFFD4FAE6)
private val backgroundLight = Color(0xFFFEF7FF)
private val errorLight = Color(0xFFE46962)
private val surfaceContainerLight = Color(0xFFF3EDF7)
private val onSurfaceVariantLight = Color(0xFF49454F)

private val primaryDark = Color(0xFF2AE881)
private val onPrimaryDark = Color(0xFF000000)
private val secondaryDark = Color(0xFFFF5F00)
private val primaryContainerDark = Color(0xFFD4FAE6)
private val backgroundDark = Color(0xFFFEF7FF)
private val errorDark = Color(0xFFE46962)
private val surfaceContainerDark = Color(0xFFF3EDF7)
private val onSurfaceVariantDark = Color(0xFF49454F)


val OriginalLightColorScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    secondary = secondaryLight,
    primaryContainer = primaryContainerLight,
    background = backgroundLight,
    error = errorLight,
    surfaceContainer = surfaceContainerLight,
    onSurfaceVariant = onSurfaceVariantLight
)

val OriginalDarkColorScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    secondary = secondaryDark,
    primaryContainer = primaryContainerDark,
    background = backgroundDark,
    error = errorDark,
    surfaceContainer = surfaceContainerDark,
    onSurfaceVariant = onSurfaceVariantDark
)