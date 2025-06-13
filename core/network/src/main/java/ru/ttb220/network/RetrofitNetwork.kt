package ru.ttb220.network

import ru.ttb220.network.model.response.AccountResponse

class RetrofitNetwork : RemoteDataSource {
    override suspend fun getAllAccounts(): List<AccountResponse> {

    }
}