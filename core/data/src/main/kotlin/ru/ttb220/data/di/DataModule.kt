package ru.ttb220.data.di

import dagger.Binds
import dagger.Module
import ru.ttb220.data.NetworkMonitor
import ru.ttb220.data.TimeProvider
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.CategoriesRepository
import ru.ttb220.data.repository.SettingsRepository
import ru.ttb220.data.repository.TransactionsRepository
import ru.ttb220.data.repository.internal.DefaultNetworkMonitor
import ru.ttb220.data.repository.internal.DefaultTimeProvider
import ru.ttb220.data.repository.internal.MockSettingsRepository
import ru.ttb220.data.repository.internal.OnlineAccountRepository
import ru.ttb220.data.repository.internal.OnlineCategoriesRepository
import ru.ttb220.data.repository.internal.OnlineTransactionsRepository
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