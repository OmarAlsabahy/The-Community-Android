package com.tawajood.the_community_user.domain.repository.language

import com.tawajood.the_community_user.app.language.AppLanguage
import kotlinx.coroutines.flow.Flow

interface ILanguageRepository {
    val languageFlow: Flow<AppLanguage>
    suspend fun setLanguage(lang: AppLanguage)
}