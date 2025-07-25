package ru.ttb220.sync.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import ru.ttb220.data.api.sync.SyncManager
import ru.ttb220.data.di.SyncablesModule
import ru.ttb220.sync.WorkManager

@Module(
    includes = [
        SyncablesModule::class,
        SyncScopeModule::class,
    ]
)
interface SyncModule {

    @Binds
    fun bindsSyncManager(workManager: WorkManager): SyncManager
}

@Module
object SyncScopeModule {

    @Provides
    @SyncScope
    fun providesApplicationScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)
}