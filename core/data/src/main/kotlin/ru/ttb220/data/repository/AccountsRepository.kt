package ru.ttb220.data.repository

import kotlinx.coroutines.flow.Flow
import ru.ttb220.model.account.Account
import ru.ttb220.model.account.AccountBrief
import ru.ttb220.model.account.AccountDetailed
import ru.ttb220.model.account.AccountHistory

interface AccountsRepository {
    fun getAllAccounts(): Flow<List<Account>>

    fun createNewAccount(account: AccountBrief): Flow<Account>

    fun getAccountById(id: Int): Flow<AccountDetailed>

    fun updateAccountById(id: Int, account: AccountBrief): Flow<Account>

    fun deleteAccountById(id: Int): Flow<Unit>

    fun getAccountHistoryById(id: Int): Flow<AccountHistory>
}