package ru.ttb220.findet.util

import androidx.compose.runtime.compositionLocalOf
import java.util.Locale

val LocalLocale = compositionLocalOf<Locale> { Locale.getDefault() }