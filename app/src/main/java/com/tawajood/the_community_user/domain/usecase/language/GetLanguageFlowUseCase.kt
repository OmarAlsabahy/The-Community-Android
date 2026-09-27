package com.tawajood.the_community_user.domain.usecase.language

import com.tawajood.the_community_user.app.language.AppLanguage
import com.tawajood.the_community_user.domain.repository.language.ILanguageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLanguageFlowUseCase @Inject constructor(
    private val repo: ILanguageRepository
) {
    operator fun invoke(): Flow<AppLanguage> = repo.languageFlow
}