package ru.ttb220.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.datetime.TimeZone

@Module
@InstallIn(SingletonComponent::class)
object TimeModule {

    @Provides
    fun provideTimeZone(): TimeZone {
        return TimeZone.currentSystemDefault()
    }
}
