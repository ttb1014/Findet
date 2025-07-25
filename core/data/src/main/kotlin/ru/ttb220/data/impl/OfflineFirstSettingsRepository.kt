package ru.ttb220.data.impl

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ru.ttb220.data.api.SettingsRepository
import ru.ttb220.data.api.sync.Syncable
import ru.ttb220.data.api.sync.Synchronizer
import ru.ttb220.data.impl.util.withExpBackoffRetry
import ru.ttb220.datastore.api.PreferencesDataSource
import ru.ttb220.model.SupportedLanguage
import ru.ttb220.model.ThemeState
import ru.ttb220.network.api.RemoteDataSource
import ru.ttb220.security.api.EncryptedPreferencesDataSource
import javax.inject.Inject

// FIXME: impl with datastore
class OfflineFirstSettingsRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val encryptedPreferencesDataSource: EncryptedPreferencesDataSource,
    private val preferencesDataSource: PreferencesDataSource,
) : SettingsRepository, Syncable {

    override fun getActiveAccountId(): Flow<Int> =
        encryptedPreferencesDataSource.observeAccountId()

    override suspend fun setActiveAccountId(id: Int): Boolean {
        encryptedPreferencesDataSource.setAccountId(id)
        return true
    }

    override fun isDarkModeEnabled(): Flow<Boolean> =
        preferencesDataSource.isDarkModeEnabledFlow

    override suspend fun setDarkModeEnabled(darkModeEnabled: Boolean): Boolean {
        preferencesDataSource.setDarkMode(darkModeEnabled)
        return true
    }

    override fun getActiveUserId(): Flow<Int> =
        encryptedPreferencesDataSource.observeUserId()

    fun setActiveUserId(id: Int) =
        encryptedPreferencesDataSource.setUserId(id)

    override suspend fun syncWith(synchronizer: Synchronizer): Boolean = coroutineScope {
        val remoteAccounts = flow { emit(remoteDataSource.getAllAccounts()) }
            .withExpBackoffRetry()
            .first()

        val accountJob = launch {
            val randomAccount = remoteAccounts
                .firstOrNull()
                ?.id ?: DEFAULT_ACCOUNT_ID
            setActiveAccountId(randomAccount)
        }

        val userJob = launch {
            val randomUserId = remoteAccounts
                .firstOrNull()
                ?.userId ?: DEFAULT_USER_ID
            setActiveUserId(randomUserId)
        }

        joinAll(accountJob, userJob)

        true
    }

    override suspend fun setThemeState(themeState: ThemeState) {
        preferencesDataSource.setThemeState(themeState)
    }

    override fun getThemeStateFlow(): Flow<ThemeState> =
        preferencesDataSource.themeStateFlow

    override suspend fun setHapticsEnabled(enabled: Boolean) {
        preferencesDataSource.setHapticsEnabled(enabled)
    }

    override fun getHapticsEnabledFlow(): Flow<Boolean> =
        preferencesDataSource.isHapticsEnabledFlow

    override suspend fun setPinCode(pin: Int) =
        encryptedPreferencesDataSource.setPinCode(pin)

    override fun verifyPinCode(pin: Int): Boolean =
        encryptedPreferencesDataSource.verifyPin(pin)

    override fun isPinSetup(): Boolean =
        encryptedPreferencesDataSource.isPinCodeSet()

    override suspend fun setSyncFrequency(freq: Long) {
        preferencesDataSource.setSyncFrequency(freq)
    }

    override fun getLastSyncFlow(): Flow<Long> =
        preferencesDataSource.lastSyncTimeFlow


    override suspend fun getLastSyncTime(): Long =
        preferencesDataSource.getLastSyncTime()

    override suspend fun setLastSyncTime(syncTime: Long) =
        preferencesDataSource.updateLastSyncTime(syncTime)

    override fun getSyncFrequencyFlow(): Flow<Long> =
        preferencesDataSource.syncFrequencyFlow

    override suspend fun getActiveLanguage(): SupportedLanguage =
        preferencesDataSource.getLanguage()

    override suspend fun setActiveLanguage(language: SupportedLanguage) =
        preferencesDataSource.setLanguage(language = language)

    override fun activeLanguageFlow(): Flow<SupportedLanguage> =
        preferencesDataSource.selectedLanguageFlow

    companion object {
        // used as a fallback.
        // TODO: resolve fallback policy
        private val DEFAULT_USER_ID = -1

        //        private val DEFAULT_USER_ID = BuildConfig.MOCK_USER_ID.toInt()
        private const val DEFAULT_DARK_THEME_MODE = false

        //        private val DEFAULT_ACCOUNT_ID = BuildConfig.MOCK_ACCOUNT_ID.toInt()
        private val DEFAULT_ACCOUNT_ID = -1
    }
}