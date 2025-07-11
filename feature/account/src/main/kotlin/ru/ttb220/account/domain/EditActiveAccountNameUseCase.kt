package ru.ttb220.account.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.ttb220.data.api.AccountsRepository
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.toAccountBrief
import javax.inject.Inject

class EditActiveAccountNameUseCase @Inject constructor(
    private val accountRepository: AccountsRepository,
    private val settingsRepository: SettingsRepository,
) {

    operator fun invoke(newName: String): Flow<SafeResult<Unit>> = flow {
        val activeAccountId = settingsRepository.getActiveAccountId().first()
        val activeAccountResult = accountRepository.getAccountById(activeAccountId).first()

        if (activeAccountResult is SafeResult.Failure) {
            emit(activeAccountResult)
            return@flow
        }

        activeAccountResult as SafeResult.Success
        val newAccountData = activeAccountResult.data
            .toAccountBrief()
            .copy(name = newName)

        val result = accountRepository.updateAccountById(
            activeAccountId,
            newAccountData
        ).first()

        if(result is SafeResult.Failure) {
            emit(result)
            return@flow
        }

        emit(SafeResult.Success(Unit))
    }
}