package com.tawajood.the_community_user.data.repository.language

import com.tawajood.the_community_user.app.language.AppLanguage
import com.tawajood.the_community_user.data.data_store.IDataStorePreferences
import com.tawajood.the_community_user.domain.repository.language.ILanguageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LanguageRepository @Inject constructor(
    private val dataStorePref: IDataStorePreferences
) : ILanguageRepository {

    override val languageFlow: Flow<AppLanguage>
        get() = dataStorePref.languageFlow

    override suspend fun setLanguage(lang: AppLanguage) {
        dataStorePref.setLanguage(lang)
    }
}