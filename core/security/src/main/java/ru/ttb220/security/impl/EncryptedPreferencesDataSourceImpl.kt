package ru.ttb220.security.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import ru.ttb220.security.api.EncryptedPreferencesDataSource
import ru.ttb220.security.impl.EncryptedPreferencesKeys.KEY_ACCOUNT_ID
import ru.ttb220.security.impl.EncryptedPreferencesKeys.KEY_BEARER_TOKEN
import ru.ttb220.security.impl.EncryptedPreferencesKeys.KEY_PIN_CODE
import ru.ttb220.security.impl.EncryptedPreferencesKeys.KEY_USER_ID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptedPreferencesDataSourceImpl @Inject constructor(
    private val prefs: SharedPreferences
) : EncryptedPreferencesDataSource {

    // User ID
    override fun setUserId(id: Int) {
        prefs.edit { putInt(KEY_USER_ID, id) }
    }

    override fun getUserId(): Int? =
        if (prefs.contains(KEY_USER_ID)) prefs.getInt(KEY_USER_ID, -1) else null

    override fun clearUserId() {
        prefs.edit { remove(KEY_USER_ID) }
    }

    override fun isUserIdSet(): Boolean = prefs.contains(KEY_USER_ID)

    // Account ID
    override fun setAccountId(id: Int) {
        prefs.edit { putInt(KEY_ACCOUNT_ID, id) }
    }

    override fun getAccountId(): Int? =
        if (prefs.contains(KEY_ACCOUNT_ID)) prefs.getInt(KEY_ACCOUNT_ID, -1) else null

    override fun clearAccountId() {
        prefs.edit { remove(KEY_ACCOUNT_ID) }
    }

    override fun isAccountIdSet(): Boolean = prefs.contains(KEY_ACCOUNT_ID)

    // Bearer Token
    override fun setBearerToken(token: String) {
        prefs.edit { putString(KEY_BEARER_TOKEN, token) }
    }

    override fun getBearerToken(): String? = prefs.getString(KEY_BEARER_TOKEN, null)

    override fun clearBearerToken() {
        prefs.edit { remove(KEY_BEARER_TOKEN) }
    }

    override fun isBearerTokenSet(): Boolean = prefs.contains(KEY_BEARER_TOKEN)

    // PIN Code
    override fun setPinCode(pin: Int) {
        prefs.edit { putInt(KEY_PIN_CODE, pin) }
    }

    override fun getPinCode(): Int? =
        if (prefs.contains(KEY_PIN_CODE)) prefs.getInt(KEY_PIN_CODE, -1) else null

    override fun clearPinCode() {
        prefs.edit { remove(KEY_PIN_CODE) }
    }

    override fun verifyPin(pin: Int): Boolean {
        val pinCode = getPinCode()
        return pinCode == pin
    }

    override fun isPinCodeSet(): Boolean = prefs.contains(KEY_PIN_CODE)

    // Clear all
    override fun clearAll() {
        prefs.edit { clear() }
    }

    override fun observeUserId(): Flow<Int> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            if (key == KEY_USER_ID) {
                trySend(prefs.getInt(key, -1))
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)
        trySend(prefs.getInt(KEY_USER_ID, -1))

        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    override fun observeAccountId(): Flow<Int> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            if (key == KEY_ACCOUNT_ID) {
                trySend(prefs.getInt(key, -1))
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)
        trySend(prefs.getInt(KEY_ACCOUNT_ID, -1))

        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }

    override fun observeBearerToken(): Flow<String?> = callbackFlow {
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            if (key == KEY_BEARER_TOKEN) {
                trySend(prefs.getString(key, ""))
            }
        }

        prefs.registerOnSharedPreferenceChangeListener(listener)
        trySend(prefs.getString(KEY_BEARER_TOKEN, ""))

        awaitClose { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
    }
}
