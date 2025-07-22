package ru.ttb220.expense.domain

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
    ): Flow<SafeResult<List<TransactionDetailed>>> =
        settingsRepository.getActiveAccountId()
            .flatMapLatest { activeAccountId ->
                val transactionsFlow = transactionsRepository.getAccountTransactionsForPeriod(
                    activeAccountId,
                    startDate = startDate,
                    endDate = endDate
                )
                val categoriesFlow = categoriesRepository.getAllCategories()
                val accountFlow = accountsRepository.getAccountById(activeAccountId)

                combine(
                    transactionsFlow,
                    categoriesFlow,
                    accountFlow
                ) { transactionsResult, categoriesResult, accountResult ->

                    if (transactionsResult is SafeResult.Failure) {
                        return@combine transactionsResult
                    }

                    if (categoriesResult !is SafeResult.Success || accountResult !is SafeResult.Success) {
                        return@combine SafeResult.Failure(DomainError.UnknownError("Unknown"))
                    }

                    val categories = categoriesResult.data
                    val account = accountResult.data

                    val filtered = (transactionsResult as SafeResult.Success).data
                        .filter { transaction ->
                            val category = categories.find { it.id == transaction.categoryId }
                            category?.isIncome == isIncome
                        }
                        .sortedBy { it.transactionDate }
                        .map { it.toTransactionDetailed(account, categories) }

                    SafeResult.Success(filtered)
                }
            }
}