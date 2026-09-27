package com.tawajood.the_community_user.domain.usecase.language

import com.tawajood.the_community_user.app.language.AppLanguage
import com.tawajood.the_community_user.domain.repository.language.ILanguageRepository
import com.tawajood.the_community_user.utils.logE
import javax.inject.Inject

class SetLanguageUseCase @Inject constructor(
    private val repo: ILanguageRepository
) {
    suspend operator fun invoke(lang: AppLanguage) {
        try {
            repo.setLanguage(lang)
        } catch (e: Exception) {
            "SetLanguageUseCase - $e".logE()
        }
    }
}