package ru.ttb220.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.datetime.Instant
import ru.ttb220.data.TimeProvider
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.TransactionsRepository
import ru.ttb220.model.transaction.TransactionDetailed
import javax.inject.Inject

class GetTransactionsForAllAccountsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
    private val timeProvider: TimeProvider,
) {
    operator fun invoke(
        isIncome: Boolean,
        startDate: Instant = timeProvider.startOfToday(),
        endDate: Instant = timeProvider.now()
    ): Flow<List<TransactionDetailed>> =
        accountsRepository.getAllAccounts().transform { accounts ->
            val result = coroutineScope {
                accounts
                    .map { account ->
                        transactionsRepository.getAccountTransactionsForPeriod(
                            accountId = account.id,
                            startDate = startDate,
                            endDate = endDate,
                        )
                    }
                    // TODO: test async
                    .map { flow ->
                        async { flow.first() }
                    }
                    .awaitAll()
                    .flatten()
                    .filter { it.category.isIncome == isIncome }
                    .sortedBy { it.transactionDate }
            }
            emit(result)
        }
}