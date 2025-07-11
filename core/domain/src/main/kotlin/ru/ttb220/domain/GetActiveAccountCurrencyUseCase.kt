package ru.ttb220.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.model.SafeResult
import javax.inject.Inject

class GetActiveAccountCurrencyUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<SafeResult<String>> = settingsRepository.getActiveAccountId()
        .flatMapLatest { accountId ->
            accountsRepository.getAccountById(accountId)
        }
        .map { accountResult ->
            when (accountResult) {
                is SafeResult.Success -> {
                    SafeResult.Success(accountResult.data.currency)
                }

                is SafeResult.Failure -> {
                    SafeResult.Failure(accountResult.cause)
                }
            }
        }
}