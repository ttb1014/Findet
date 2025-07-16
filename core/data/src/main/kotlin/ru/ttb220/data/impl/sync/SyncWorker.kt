package ru.ttb220.data.impl.sync

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import ru.ttb220.data.api.SyncableAccountsRepository
import ru.ttb220.data.api.SyncableCategoriesRepository
import ru.ttb220.data.api.SyncableTransactionsRepository
import ru.ttb220.data.api.sync.Synchronizer
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val accountsRepository: SyncableAccountsRepository,
    private val categoriesRepository: SyncableCategoriesRepository,
    private val transactionsRepository: SyncableTransactionsRepository
) : CoroutineWorker(context, params), Synchronizer {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val syncedSuccessfully = awaitAll(
            async { accountsRepository.sync() },
            async { categoriesRepository.sync() },
            async { transactionsRepository.sync() }
        ).all { it }

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
    @AssistedFactory
    interface Factory {
        fun create(
            context: Context,
            params: WorkerParameters
        ): SyncWorker
    }
}