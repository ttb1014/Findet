package ru.ttb220.bottomsheet.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import javax.inject.Inject

class SetActiveAccountCurrencyUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository,
) {
    operator fun invoke(
        currency: String
    ): Flow<SafeResult<Unit>> = settingsRepository.getActiveAccountId()
        .map { accountId ->
            val accountResult = accountsRepository.getAccountById(accountId).first()

            when (accountResult) {
                is SafeResult.Failure -> {
                    SafeResult.Failure(accountResult.cause)
                }

                is SafeResult.Success<Account> -> {
                    val updateResult = accountsRepository.updateAccountById(
                        accountId,
                        account = AccountBrief(
                            name = accountResult.data.name,
                            balance = accountResult.data.balance,
                            currency = currency,
                        )
                    ).first()

                    when (updateResult) {
                        is SafeResult.Failure -> updateResult
                        is SafeResult.Success<*> ->
                            SafeResult.Success(Unit)
                    }
                }
            }
        }
}