package ru.ttb220.data.di

import dagger.Binds
import dagger.Module
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.CategoriesRepository
import ru.ttb220.data.api.NetworkMonitor
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.data.impl.DefaultNetworkMonitor
import ru.ttb220.data.impl.DefaultTimeProvider
import ru.ttb220.data.impl.OfflineFirstAccountsRepository
import ru.ttb220.data.impl.OfflineFirstCategoriesRepository
import ru.ttb220.data.impl.OfflineFirstSettingsRepository
import ru.ttb220.data.impl.OfflineFirstTransactionsRepository
import ru.ttb220.database.di.DatabaseModule
import ru.ttb220.datastore.di.PreferencesModule
import ru.ttb220.network.di.NetworkModule
import ru.ttb220.security.di.EncryptedPreferencesModule
import javax.inject.Singleton

@Module(
    includes = [
        TimeModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        PreferencesModule::class,
        EncryptedPreferencesModule::class
    ]
)
interface DataModule {

    @Singleton
    @Binds
    fun bindsAccountsRepository(
        offlineFirstAccountsRepository: OfflineFirstAccountsRepository
    ): AccountsRepository

    @Singleton
    @Binds
    fun bindsCategoriesRepository(
        offlineFirstCategoriesRepository: OfflineFirstCategoriesRepository
    ): CategoriesRepository

    @Singleton
    @Binds
    fun bindsTransactionsRepository(
        offlineFirstTransactionsRepository: OfflineFirstTransactionsRepository
    ): TransactionsRepository

    @Singleton
    @Binds
    fun bindsSettingsRepository(
        offlineFirstSettingsRepository: OfflineFirstSettingsRepository
    ): SettingsRepository

    @Singleton
    @Binds
    fun bindsTimeProvider(
        defaultTimeProvider: DefaultTimeProvider
    ): TimeProvider

    @Singleton
    @Binds
    fun bindsNetworkMonitor(
        defaultNetworkMonitor: DefaultNetworkMonitor
    ): NetworkMonitor
}