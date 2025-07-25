package ru.ttb220.datastore.impl

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val THEME_STATE = stringPreferencesKey("theme_state")
    val SYNC_FREQUENCY = longPreferencesKey("sync_frequency")
    val IS_HAPTICS_ENABLED = stringPreferencesKey("is_haptics_enabled")
    val LAST_SYNC_TIME = longPreferencesKey("last_sync_time")
    val LANGUAGE_KEY = stringPreferencesKey("language")
    val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")
}