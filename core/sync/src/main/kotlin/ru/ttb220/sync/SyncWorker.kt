package ru.ttb220.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.datastore.api.PreferencesDataSource
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @Named("accountsRepository")
    private val accountsRepository: Syncable,
    @Named("categoriesRepository")
    private val categoriesRepository: Syncable,
    @Named("transactionsRepository")
    private val transactionsRepository: Syncable,
    @Named("settingsRepository")
    private val settingsRepository: Syncable,
    private val timeProvider: TimeProvider,
    private val preferencesDataSource: PreferencesDataSource,
) : CoroutineWorker(context, params), Synchronizer {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val syncedSuccessfully =
            settingsRepository.sync() &&
            categoriesRepository.sync() &&
                    accountsRepository.sync() &&
                    transactionsRepository.sync() &&
                    true

        if (syncedSuccessfully) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        fun periodicSyncWork() = PeriodicWorkRequestBuilder<DelegatingWorker>(
            repeatInterval = 6,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(
                SyncConstraints
            )
            .setInputData(SyncWorker::class.delegatedData())
            .build()

        fun startUpSyncWork() = OneTimeWorkRequestBuilder<DelegatingWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(SyncConstraints)
            .setInputData(SyncWorker::class.delegatedData())
            .build()
    }

    @Singleton
    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(
            context: Context,
            params: WorkerParameters
        ): SyncWorker
    }
}