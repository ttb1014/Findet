package ru.ttb220.findet.di

import android.content.Context
import ru.ttb220.findet.FindetApplication

@Suppress("RecursivePropertyAccessor")
val Context.appComponent: AppComponent
    get() = when (this) {
        is FindetApplication -> appComponent
        else -> applicationContext.appComponent
    }