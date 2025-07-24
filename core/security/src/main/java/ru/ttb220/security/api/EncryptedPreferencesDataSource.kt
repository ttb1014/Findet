package ru.ttb220.security.api

interface EncryptedPreferencesDataSource {
    // User ID
    fun setUserId(id: String)
    fun getUserId(): String?
    fun clearUserId()
    fun isUserIdSet(): Boolean

    // Account ID
    fun setAccountId(id: String)
    fun getAccountId(): String?
    fun clearAccountId()
    fun isAccountIdSet(): Boolean

    // Bearer Token
    fun setBearerToken(token: String)
    fun getBearerToken(): String?
    fun clearBearerToken()
    fun isBearerTokenSet(): Boolean

    // PIN Code
    fun setPinCode(pin: String)
    fun getPinCode(): String?
    fun clearPinCode()
    fun isPinCodeSet(): Boolean

    fun clearAll()
}