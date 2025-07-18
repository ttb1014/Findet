package ru.ttb220.data.api

import kotlinx.coroutines.flow.Flow
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.model.account.AccountDetailed

/**
 * Each method provides either wrapped data or wrapped domain error.
 */
interface AccountsRepository {

    fun getAllAccounts(): Flow<SafeResult<List<Account>>>

    fun getAccountById(id: Int): Flow<SafeResult<Account>>

    fun createNewAccount(account: AccountBrief): Flow<SafeResult<Account>>

    fun updateAccountById(id: Int, account: AccountBrief): Flow<SafeResult<Account>>

    fun deleteAccountById(id: Int): Flow<SafeResult<Unit>>
}