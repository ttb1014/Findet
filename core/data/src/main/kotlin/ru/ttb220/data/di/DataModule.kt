package ru.ttb220.data.di

import dagger.Binds
import dagger.Module
import ru.ttb220.data.api.NetworkMonitor
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.legacy.AccountsRepository
import ru.ttb220.data.api.legacy.CategoriesRepository
import ru.ttb220.data.api.legacy.TransactionsRepository
import ru.ttb220.data.impl.legacy.DefaultNetworkMonitor
import ru.ttb220.data.impl.legacy.DefaultTimeProvider
import ru.ttb220.data.impl.legacy.MockSettingsRepository
import ru.ttb220.data.impl.legacy.OnlineAccountRepository
import ru.ttb220.data.impl.legacy.OnlineCategoriesRepository
import ru.ttb220.data.impl.legacy.OnlineTransactionsRepository
import ru.ttb220.database.di.DatabaseModule
import ru.ttb220.network.di.NetworkModule

@Module(
    includes = [
        TimeModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        SyncableDataModule::class
    ]
)
interface DataModule {

    @Binds
    fun bindsAccountsRepository(
        onlineAccountRepository: OnlineAccountRepository
    ): AccountsRepository

    @Binds
    fun bindsCategoriesRepository(
        onlineCategoriesRepository: OnlineCategoriesRepository
    ): CategoriesRepository

    @Binds
    fun bindsTransactionsRepository(
        onlineTransactionsRepository: OnlineTransactionsRepository
    ): TransactionsRepository

    @Binds
    fun bindsMockSettingsRepository(
        mockSettingsRepository: MockSettingsRepository
    ): SettingsRepository

    @Binds
    fun bindsTimeProvider(
        defaultTimeProvider: DefaultTimeProvider
    ): TimeProvider

    @Binds
    fun bindsNetworkMonitor(
        defaultNetworkMonitor: DefaultNetworkMonitor
    ): NetworkMonitor
}