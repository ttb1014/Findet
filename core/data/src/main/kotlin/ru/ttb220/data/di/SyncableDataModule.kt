package ru.ttb220.data.di

import dagger.Binds
import dagger.Module
import ru.ttb220.data.impl.OfflineFirstCategoriesRepository
import ru.ttb220.data.api.SyncableAccountsRepository
import ru.ttb220.data.api.SyncableCategoriesRepository
import ru.ttb220.data.api.SyncableTransactionsRepository
import ru.ttb220.data.impl.OfflineFirstAccountsRepository
import ru.ttb220.data.impl.OfflineFirstTransactionsRepository

@Module
interface SyncableDataModule {
    @Binds
    fun bindsAccountsRepository(
        offlineFirstAccountsRepository: OfflineFirstAccountsRepository
    ): SyncableAccountsRepository

    @Binds
    fun bindsCategoriesRepository(
        offlineFirstCategoriesRepository: OfflineFirstCategoriesRepository
    ): SyncableCategoriesRepository

    @Binds
    fun bindsTransactionsRepository(
        offlineFirstTransactionsRepository: OfflineFirstTransactionsRepository
    ): SyncableTransactionsRepository
}