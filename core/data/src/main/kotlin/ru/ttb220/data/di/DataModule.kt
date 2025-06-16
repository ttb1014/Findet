package ru.ttb220.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.CategoriesRepository
import ru.ttb220.data.repository.TransactionsRepository
import ru.ttb220.data.repository.internal.OnlineAccountRepository
import ru.ttb220.data.repository.internal.OnlineCategoriesRepository
import ru.ttb220.data.repository.internal.OnlineTransactionsRepository

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
}