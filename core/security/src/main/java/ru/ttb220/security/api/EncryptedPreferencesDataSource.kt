package ru.ttb220.security.api

import kotlinx.coroutines.flow.Flow

interface EncryptedPreferencesDataSource {
    // User ID
    fun setUserId(id: Int)
    fun getUserId(): Int?
    fun clearUserId()
    fun isUserIdSet(): Boolean
    fun observeUserId(): Flow<Int>

    // Account ID
    fun setAccountId(id: Int)
    fun getAccountId(): Int?
    fun clearAccountId()
    fun isAccountIdSet(): Boolean
    fun observeAccountId(): Flow<Int>

    // Bearer Token
    fun setBearerToken(token: String)
    fun getBearerToken(): String?
    fun clearBearerToken()
    fun isBearerTokenSet(): Boolean
    fun observeBearerToken(): Flow<String?>

    // PIN Code
    fun setPinCode(pin: Int)
    fun getPinCode(): Int?
    fun clearPinCode()
    fun isPinCodeSet(): Boolean
    fun verifyPin(pin: Int): Boolean

    fun clearAll()
}