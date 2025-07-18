package ru.ttb220.sync.di

import dagger.Binds
import dagger.Module
import ru.ttb220.data.api.sync.SyncManager
import ru.ttb220.data.di.SyncablesModule
import ru.ttb220.sync.WorkManager

@Module(
    includes = [
        SyncablesModule::class
    ]
)
interface SyncModule {

    @Binds
    fun bindsSyncManager(workManager: WorkManager): SyncManager
}