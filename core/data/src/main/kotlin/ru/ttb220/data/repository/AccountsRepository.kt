package ru.ttb220.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.model.account.AccountDetailed
import ru.ttb220.model.account.AccountHistory

interface AccountsRepository {
    fun getAllAccounts(): Flow<Result<List<Account>>>

    fun createNewAccount(account: AccountBrief): Flow<Result<Account>>

    fun getAccountById(id: Int): Flow<Result<AccountDetailed>>

    fun updateAccountById(id: Int, account: AccountBrief): Flow<Result<Account>>

    fun deleteAccountById(id: Int): Flow<Result<Unit>>

    fun getAccountHistoryById(id: Int): Flow<Result<AccountHistory>>
}