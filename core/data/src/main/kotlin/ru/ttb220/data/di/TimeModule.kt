package ru.ttb220.data.di

import dagger.Module
import dagger.Provides
import kotlinx.datetime.TimeZone
import javax.inject.Singleton

@Module
object TimeModule {

    @Provides
    @Singleton
    fun provideTimeZone(): TimeZone {
        return TimeZone.currentSystemDefault()
    }
}
