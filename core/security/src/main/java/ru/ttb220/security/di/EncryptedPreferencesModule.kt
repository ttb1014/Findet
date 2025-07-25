package ru.ttb220.security.di

import dagger.Binds
import dagger.Module
import ru.ttb220.security.api.EncryptedPreferencesDataSource
import ru.ttb220.security.impl.EncryptedPreferencesDataSourceImpl
import javax.inject.Singleton

@Module(
    includes = [
        SecurityModule::class
    ]
)
interface EncryptedPreferencesModule {

    @Singleton
    @Binds
    fun bindEncryptedPreferencesDataSource(
        encryptedPreferencesDataSourceImpl: EncryptedPreferencesDataSourceImpl
    ): EncryptedPreferencesDataSource
}