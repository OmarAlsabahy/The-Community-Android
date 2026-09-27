package com.tawajood.the_community_user.data.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.tawajood.the_community_user.app.language.AppLanguage
import com.tawajood.the_community_user.data.data_store.DataStorePreferences.PreferenceKeys.LANGUAGE_KEY
import com.tawajood.the_community_user.data.data_store.DataStorePreferences.PreferenceKeys.ON_BOARDING_KEY
import com.tawajood.the_community_user.data.data_store.DataStorePreferences.PreferenceKeys.TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferences @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IDataStorePreferences {

    private object PreferenceKeys {
        val LANGUAGE_KEY = stringPreferencesKey("app_language")
        val ON_BOARDING_KEY = booleanPreferencesKey("on_boarding")
        val TOKEN_KEY = stringPreferencesKey("token")
    }

    override val languageFlow: Flow<AppLanguage> =
        dataStore.data.map { prefs ->
            AppLanguage.fromCode(prefs[LANGUAGE_KEY]?.ifBlank { null } ?: AppLanguage.ARABIC.code)
        }

    override suspend fun setLanguage(lang: AppLanguage) {
        dataStore.edit { it[LANGUAGE_KEY] = lang.code }
    }

    override suspend fun setOnBoardingShown() {
        dataStore.edit { it[ON_BOARDING_KEY] = true }
    }

    override suspend fun isOnBoardingShown(): Boolean {
        val prefs = dataStore.data.first()
        return prefs[ON_BOARDING_KEY] ?: false
    }

    override val tokenFlow: Flow<String?> = dataStore.data.map { prefs ->
        prefs[TOKEN_KEY]
    }

    override suspend fun setUserToken(token: String) {
        dataStore.edit { it[TOKEN_KEY] = token }
    }

    override suspend fun getUserToken(): String? {
        val prefs = dataStore.data.first()
        return prefs[TOKEN_KEY]
    }

    override suspend fun clearToken() {
        dataStore.edit { it.remove(TOKEN_KEY) }
    }
}