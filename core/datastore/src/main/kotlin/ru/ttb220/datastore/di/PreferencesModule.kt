package ru.ttb220.datastore.di

import dagger.Binds
import dagger.Module
import ru.ttb220.datastore.api.PreferencesDataSource
import ru.ttb220.datastore.impl.PreferencesDataSourceImpl

@Module(
    includes = [
        DataStoreModule::class
    ]
)
interface PreferencesModule {

    @Binds
    fun bindPreferencesDataSource(
        preferencesDataSourceImpl: PreferencesDataSourceImpl
    ): PreferencesDataSource
}