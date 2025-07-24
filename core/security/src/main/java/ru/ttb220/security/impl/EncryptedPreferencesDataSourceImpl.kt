package ru.ttb220.security.impl

import android.content.SharedPreferences
import ru.ttb220.security.api.EncryptedPreferencesDataSource
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit
import ru.ttb220.security.impl.EncryptedPreferencesKeys.KEY_USER_ID
import ru.ttb220.security.impl.EncryptedPreferencesKeys.KEY_ACCOUNT_ID
import ru.ttb220.security.impl.EncryptedPreferencesKeys.KEY_PIN_CODE
import ru.ttb220.security.impl.EncryptedPreferencesKeys.KEY_BEARER_TOKEN

@Singleton
class EncryptedPreferencesDataSourceImpl @Inject constructor(
    private val prefs: SharedPreferences
) : EncryptedPreferencesDataSource {

    override fun setUserId(id: String) {
        prefs.edit { putString(KEY_USER_ID, id) }
    }

    override fun getUserId(): String? = prefs.getString(KEY_USER_ID, null)

    override fun clearUserId() {
        prefs.edit { remove(KEY_USER_ID) }
    }

    override fun isUserIdSet(): Boolean = prefs.contains(KEY_USER_ID)

    override fun setAccountId(id: String) {
        prefs.edit { putString(KEY_ACCOUNT_ID, id) }
    }

    override fun getAccountId(): String? = prefs.getString(KEY_ACCOUNT_ID, null)

    override fun clearAccountId() {
        prefs.edit { remove(KEY_ACCOUNT_ID) }
    }

    override fun isAccountIdSet(): Boolean = prefs.contains(KEY_ACCOUNT_ID)

    override fun setBearerToken(token: String) {
        prefs.edit { putString(KEY_BEARER_TOKEN, token) }
    }

    override fun getBearerToken(): String? = prefs.getString(KEY_BEARER_TOKEN, null)

    override fun clearBearerToken() {
        prefs.edit { remove(KEY_BEARER_TOKEN) }
    }

    override fun isBearerTokenSet(): Boolean = prefs.contains(KEY_BEARER_TOKEN)

    override fun setPinCode(pin: String) {
        prefs.edit { putString(KEY_PIN_CODE, pin) }
    }

    override fun getPinCode(): String? = prefs.getString(KEY_PIN_CODE, null)

    override fun clearPinCode() {
        prefs.edit { remove(KEY_PIN_CODE) }
    }

    override fun isPinCodeSet(): Boolean = prefs.contains(KEY_PIN_CODE)

    override fun clearAll() {
        prefs.edit { clear() }
    }
}
