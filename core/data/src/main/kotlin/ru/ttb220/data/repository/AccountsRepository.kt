package ru.ttb220.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ttb220.model.SafeResult
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.model.account.AccountDetailed
import ru.ttb220.model.account.AccountHistory

/**
 * Each method provides either wrapped data or wrapped domain error.
 */
interface AccountsRepository {
    fun getAllAccounts(): Flow<SafeResult<List<Account>>>

    fun createNewAccount(account: AccountBrief): Flow<SafeResult<Account>>

    fun getAccountById(id: Int): Flow<SafeResult<AccountDetailed>>

    fun updateAccountById(id: Int, account: AccountBrief): Flow<SafeResult<Account>>

    fun deleteAccountById(id: Int): Flow<SafeResult<Unit>>

    fun getAccountHistoryById(id: Int): Flow<SafeResult<AccountHistory>>
}