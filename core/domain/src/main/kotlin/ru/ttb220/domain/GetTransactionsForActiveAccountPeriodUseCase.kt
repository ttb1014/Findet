package ru.ttb220.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.legacy.TransactionsRepository
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.TransactionDetailed
import javax.inject.Inject

class GetTransactionsForActiveAccountPeriodUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val settingsRepository: SettingsRepository,
) {
    operator fun invoke(
        isIncome: Boolean,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<SafeResult<List<TransactionDetailed>>> =
        settingsRepository.getActiveAccountId()
            .map { activeAccountId ->
                val transactionsResult = transactionsRepository.getAccountTransactionsForPeriod(
                    activeAccountId,
                    startDate = startDate,
                    endDate = endDate,
                ).first()

                when (transactionsResult) {
                    is SafeResult.Failure -> {
                        transactionsResult
                    }

                    is SafeResult.Success -> {
                        SafeResult.Success(
                            transactionsResult.data
                                .filter { it.category.isIncome == isIncome }
                                .sortedBy { it.transactionDate }
                        )
                    }
                }
            }
}