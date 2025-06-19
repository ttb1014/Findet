package ru.ttb220.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.data.repository.SettingsRepository
import javax.inject.Inject

class GetActiveAccountCurrencyUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<String> = settingsRepository.getActiveAccountId()
        .flatMapLatest { accountId ->
            accountsRepository.getAccountById(accountId)
        }
        .map { account ->
            account.currency
        }
}