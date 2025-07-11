package ru.ttb220.data.di

import dagger.Module
import dagger.Provides
import kotlinx.datetime.TimeZone

@Module
object TimeModule {

    @Provides
    fun provideTimeZone(): TimeZone {
        return TimeZone.currentSystemDefault()
    }
}
