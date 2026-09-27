package com.tawajood.the_community_user.data.data_store

import com.tawajood.the_community_user.app.language.AppLanguage
import kotlinx.coroutines.flow.Flow

interface IDataStorePreferences {
    // language
    val languageFlow: Flow<AppLanguage>
    suspend fun setLanguage(lang: AppLanguage)
    suspend fun setOnBoardingShown()
    suspend fun isOnBoardingShown(): Boolean

    // token
    val tokenFlow: Flow<String?>
    suspend fun setUserToken(token: String)
    suspend fun getUserToken(): String?
    suspend fun clearToken()
}
