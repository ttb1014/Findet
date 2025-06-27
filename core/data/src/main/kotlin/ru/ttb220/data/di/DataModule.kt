package ru.ttb220.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ttb220.data.NetworkMonitor
import ru.ttb220.data.TimeProvider
import ru.ttb220.data.internal.DefaultNetworkMonitor
import ru.ttb220.data.internal.DefaultTimeProvider
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.CategoriesRepository
import ru.ttb220.data.repository.SettingsRepository
import ru.ttb220.data.repository.TransactionsRepository
import ru.ttb220.data.repository.internal.MockSettingsRepository
import ru.ttb220.data.repository.internal.OnlineAccountRepository
import ru.ttb220.data.repository.internal.OnlineCategoriesRepository
import ru.ttb220.data.repository.internal.OnlineTransactionsRepository
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsAccountsRepository(
        onlineAccountRepository: OnlineAccountRepository
    ): AccountsRepository

    @Binds
    internal abstract fun bindsCategoriesRepository(
        onlineCategoriesRepository: OnlineCategoriesRepository
    ): CategoriesRepository

    @Binds
    internal abstract fun bindsTransactionsRepository(
        onlineTransactionsRepository: OnlineTransactionsRepository
    ): TransactionsRepository

    @Binds
//    @Named("mock")
    internal abstract fun bindsMockSettingsRepository(
        mockSettingsRepository: MockSettingsRepository
    ): SettingsRepository

    @Binds
    internal abstract fun bindsTimeProvider(
        defaultTimeProvider: DefaultTimeProvider
    ): TimeProvider

    @Binds
    internal abstract fun bindsNetworkMonitor(
        defaultNetworkMonitor: DefaultNetworkMonitor
    ): NetworkMonitor
}