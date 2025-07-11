package ru.ttb220.data.di

import dagger.Binds
import dagger.Module
import ru.ttb220.data.api.NetworkMonitor
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.CategoriesRepository
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.data.impl.DefaultNetworkMonitor
import ru.ttb220.data.impl.DefaultTimeProvider
import ru.ttb220.data.impl.MockSettingsRepository
import ru.ttb220.data.impl.OnlineAccountRepository
import ru.ttb220.data.impl.OnlineCategoriesRepository
import ru.ttb220.data.impl.OnlineTransactionsRepository
import ru.ttb220.network.di.NetworkModule

@Module(
    includes = [
        TimeModule::class,
        NetworkModule::class
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