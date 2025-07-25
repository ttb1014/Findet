package ru.ttb220.account.domain

import kotlinx.coroutines.flow.first
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.Transaction
import javax.inject.Inject

class GetMonthTransactionsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val settingsRepository: SettingsRepository,
    private val timeProvider: TimeProvider,
) {

    /**
     * @throws ClassCastException
     */
    // FIXME: wrap with try catch
    suspend operator fun invoke(): List<Transaction> {
        val accountId = settingsRepository.getActiveAccountId().first()

        return (transactionsRepository.getAccountTransactionsForPeriod(
            accountId = accountId,
            startDate = timeProvider.dayMonthAgo(),
            endDate = timeProvider.today()
        ).first() as SafeResult.Success).data
    }
}