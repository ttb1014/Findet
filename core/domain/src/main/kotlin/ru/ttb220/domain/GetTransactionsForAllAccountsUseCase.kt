package ru.ttb220.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.datetime.LocalDate
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.TransactionsRepository
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.TransactionDetailed
import javax.inject.Inject

class GetTransactionsForAllAccountsUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
) {
    operator fun invoke(
        isIncome: Boolean,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<SafeResult<List<TransactionDetailed>>> =
        accountsRepository.getAllAccounts()
            .transform { accountsResult ->
                if (accountsResult is SafeResult.Failure) {
                    emit(accountsResult)
                    return@transform
                }

                // if successfully retrieved accounts -> map it to success
                accountsResult as SafeResult.Success

                val result = coroutineScope {
                    val transactionResults = accountsResult.data
                        .map { account ->
                            transactionsRepository.getAccountTransactionsForPeriod(
                                accountId = account.id,
                                startDate = startDate,
                                endDate = endDate,
                            )
                        }
                        .map { flow ->
                            async { flow.first() }
                        }
                        .awaitAll()

                    // any request failed -> return first failure reason
                    if (transactionResults.any { it is SafeResult.Failure }) {
                        val failure = transactionResults.first { it is SafeResult.Failure }

                        failure
                    } else {
                        // else return success
                        val success = SafeResult.Success(transactionResults.map {
                            it as SafeResult.Success
                            it.data
                        }.flatten()
                            .filter { it.category.isIncome == isIncome }
                            .sortedBy { it.transactionDate }
                        )

                        success
                    }
                }

                emit(result)
            }
}