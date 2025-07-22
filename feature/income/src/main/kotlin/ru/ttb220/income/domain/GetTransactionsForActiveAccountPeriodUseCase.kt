package ru.ttb220.income.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.datetime.LocalDate
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.CategoriesRepository
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.TransactionsRepository
import ru.ttb220.model.DomainError
import ru.ttb220.model.SafeResult
import ru.ttb220.model.transaction.TransactionDetailed
import ru.ttb220.model.mapper.toTransactionDetailed
import javax.inject.Inject

class GetTransactionsForActiveAccountPeriodUseCase @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val accountsRepository: AccountsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val settingsRepository: SettingsRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        isIncome: Boolean,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<SafeResult<List<TransactionDetailed>>> {
        return settingsRepository.getActiveAccountId()
            .flatMapLatest { activeAccountId ->
                val accountFlow = accountsRepository.getAccountById(activeAccountId)
                val categoriesFlow = categoriesRepository.getAllCategories()
                val transactionsFlow = transactionsRepository.getAccountTransactionsForPeriod(
                    accountId = activeAccountId,
                    startDate = startDate,
                    endDate = endDate
                )

                combine(
                    accountFlow,
                    categoriesFlow,
                    transactionsFlow
                ) { accountResult, categoriesResult, transactionsResult ->

                    if (accountResult !is SafeResult.Success) {
                        return@combine SafeResult.Failure(DomainError.UnknownError("Account unavailable"))
                    }

                    if (categoriesResult !is SafeResult.Success) {
                        return@combine SafeResult.Failure(DomainError.UnknownError("Categories unavailable"))
                    }

                    if (transactionsResult !is SafeResult.Success) {
                        return@combine SafeResult.Failure(DomainError.UnknownError("Transactions unavailable"))
                    }

                    val account = accountResult.data
                    val categories = categoriesResult.data
                    val transactions = transactionsResult.data

                    val detailed = transactions
                        .map { it.toTransactionDetailed(account, categories) }
                        .filter { it.category.isIncome == isIncome }
                        .sortedBy { it.transactionDate }

                    SafeResult.Success(detailed)
                }
            }
    }

}