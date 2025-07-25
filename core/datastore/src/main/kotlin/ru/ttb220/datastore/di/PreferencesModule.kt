package ru.ttb220.datastore.di

import dagger.Binds
import dagger.Module
import ru.ttb220.datastore.api.PreferencesDataSource
import ru.ttb220.datastore.impl.PreferencesDataSourceImpl
import javax.inject.Singleton

@Module(
    includes = [
        DataStoreModule::class
    ]
)
interface PreferencesModule {

    @Singleton
    @Binds
    fun bindPreferencesDataSource(
        preferencesDataSourceImpl: PreferencesDataSourceImpl
    ): PreferencesDataSource
}