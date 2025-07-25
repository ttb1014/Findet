package ru.ttb220.findet.util

import android.content.Context
import android.content.res.Configuration
import ru.ttb220.findet.FindetApplication
import ru.ttb220.findet.di.AppComponent
import ru.ttb220.model.SupportedLanguage
import java.util.Locale

@Suppress("RecursivePropertyAccessor")
val Context.appComponent: AppComponent
    get() = when (this) {
        is FindetApplication -> appComponent
        else -> applicationContext.appComponent
    }

fun Context.withLocale(locale: Locale): Context {
    val config = resources.configuration
    val newConfig = Configuration(config)
    newConfig.setLocale(locale)
    return createConfigurationContext(newConfig)
}