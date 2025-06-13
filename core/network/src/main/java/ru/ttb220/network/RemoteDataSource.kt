package ru.ttb220.network

import ru.ttb220.network.model.response.AccountResponse

interface RemoteDataSource {
    suspend fun getAllAccounts(): List<AccountResponse>
}