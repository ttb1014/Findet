package ru.ttb220.incomes.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.ttb220.data.api.TimeProvider
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.TransactionDetailed
import javax.inject.Inject

// we may use GetTransactionsForActiveAccountPeriodUseCase instead
class GetTodayIncomesForActiveAccountUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val settingsRepository: SettingsRepository,
    private val timeProvider: TimeProvider,
) {
    operator fun invoke(): Flow<SafeResult<List<TransactionDetailed>>> =
        settingsRepository.getActiveAccountId()
            .map { activeAccountId ->
                val today = timeProvider.today().first()

                val transactionsResult = transactionsRepository.getAccountTransactionsForPeriod(
                    activeAccountId,
                    startDate = today,
                    endDate = today,
                ).first()

                when (transactionsResult) {
                    is SafeResult.Success -> {
                        SafeResult.Success(
                            transactionsResult.data
                                .filter { it.category.isIncome }
                                .sortedBy { it.transactionDate }
                        )
                    }

                    is SafeResult.Failure -> {
                        SafeResult.Failure(
                            transactionsResult.cause
                        )
                    }
                }
            }
}