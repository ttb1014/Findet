package ru.ttb220.app.di

import android.content.Context
import ru.ttb220.app.FindetApplication

@Suppress("RecursivePropertyAccessor")
val Context.appComponent: AppComponent
    get() = when (this) {
        is FindetApplication -> appComponent
        else -> applicationContext.appComponent
    }