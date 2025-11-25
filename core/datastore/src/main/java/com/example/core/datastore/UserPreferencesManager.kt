package com.example.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

/**
 * User preferences manager using DataStore.
 */
@Singleton
class UserPreferencesManager @Inject constructor(
    private val context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        // Theme preferences
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val THEME_COLOR_KEY = intPreferencesKey("theme_color")
        
        // User preferences
        val USER_NAME_KEY = stringPreferencesKey("user_name")
        val USER_ID_KEY = stringPreferencesKey("user_id")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
        val LAST_LOGIN_TIME_KEY = longPreferencesKey("last_login_time")
        
        // App preferences
        val LANGUAGE_KEY = stringPreferencesKey("language")
        val NOTIFICATION_ENABLED_KEY = booleanPreferencesKey("notification_enabled")
        val TEMPERATURE_UNIT_KEY = stringPreferencesKey("temperature_unit")
        
        // Weather preferences
        val DEFAULT_CITY_KEY = stringPreferencesKey("default_city")
        val LAST_UPDATED_KEY = longPreferencesKey("last_updated")
    }

    // Theme preferences
    val darkModeFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    val themeColorFlow: Flow<Int> = dataStore.data.map { preferences ->
        preferences[THEME_COLOR_KEY] ?: 0
    }

    suspend fun setThemeColor(color: Int) {
        dataStore.edit { preferences ->
            preferences[THEME_COLOR_KEY] = color
        }
    }

    // User preferences
    val userNameFlow: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY]
    }

    suspend fun setUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    val isLoggedInFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_LOGGED_IN_KEY] ?: false
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    // Weather preferences
    val defaultCityFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[DEFAULT_CITY_KEY] ?: "London"
    }

    suspend fun setDefaultCity(city: String) {
        dataStore.edit { preferences ->
            preferences[DEFAULT_CITY_KEY] = city
        }
    }

    val temperatureUnitFlow: Flow<String> = dataStore.data.map { preferences ->
        preferences[TEMPERATURE_UNIT_KEY] ?: "metric"
    }

    suspend fun setTemperatureUnit(unit: String) {
        dataStore.edit { preferences ->
            preferences[TEMPERATURE_UNIT_KEY] = unit
        }
    }

    // App preferences
    val notificationEnabledFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[NOTIFICATION_ENABLED_KEY] ?: true
    }

    suspend fun setNotificationEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_ENABLED_KEY] = enabled
        }
    }

    // Clear all preferences
    suspend fun clearAll() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
