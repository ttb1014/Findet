package ru.ttb220.findet.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.Locale

@Composable
fun ProvideLocalizedContext(
    locale: Locale,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val localizedContext = remember(locale) { context.withLocale(locale) }

    CompositionLocalProvider(
        LocalLocale provides locale,
        LocalContext provides localizedContext,
        content = content
    )
}
