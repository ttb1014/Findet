package ru.ttb220.data.di

import dagger.Binds
import dagger.Module
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.data.impl.OfflineFirstAccountsRepository
import ru.ttb220.data.impl.OfflineFirstCategoriesRepository
import ru.ttb220.data.impl.OfflineFirstSettingsRepository
import ru.ttb220.data.impl.OfflineFirstTransactionsRepository
import javax.inject.Named

@Module
interface SyncablesModule {

    @Binds
    @Named("accountsRepository")
    fun bindAccountsRepository(offlineFirstAccountsRepository: OfflineFirstAccountsRepository): Syncable

    @Binds
    @Named("transactionsRepository")
    fun bindTransactionsRepository(offlineFirstTransactionsRepository: OfflineFirstTransactionsRepository): Syncable

    @Binds
    @Named("categoriesRepository")
    fun bindCategoriesRepository(offlineFirstCategoriesRepository: OfflineFirstCategoriesRepository): Syncable

    @Binds
    @Named("settingsRepository")
    fun bindSettingsRepository(offlineFirstSettingsRepository: OfflineFirstSettingsRepository): Syncable
}