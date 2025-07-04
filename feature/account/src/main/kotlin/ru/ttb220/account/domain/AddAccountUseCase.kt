package ru.ttb220.account.domain

import kotlinx.coroutines.flow.Flow
import ru.ttb220.data.repository.AccountsRepository
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository
) {
    operator fun invoke(account: AccountBrief): Flow<SafeResult<Account>> =
        accountsRepository.createNewAccount(
            account = account
        )
}