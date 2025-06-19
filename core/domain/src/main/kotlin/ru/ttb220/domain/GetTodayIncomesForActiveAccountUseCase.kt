package ru.ttb220.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.ttb220.data.TimeProvider
import ru.ttb220.data.repository.SettingsRepository
import ru.ttb220.data.repository.TransactionsRepository
import ru.ttb220.model.transaction.TransactionDetailed
import javax.inject.Inject

class GetTodayIncomesForActiveAccountUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val settingsRepository: SettingsRepository,
    private val timeProvider: TimeProvider,
) {
    operator fun invoke(): Flow<List<TransactionDetailed>> =
        settingsRepository.getActiveAccountId()
            .map { activeAccountId ->
                transactionsRepository.getAccountTransactionsForPeriod(
                    activeAccountId,
                    startDate = timeProvider.today(),
                    endDate = timeProvider.today(),
                )
                    .first()
                    .filter { it.category.isIncome }
                    .sortedBy { it.transactionDate }
            }
}