package com.tawajood.the_community_user.domain.usecase.on_boarding

import com.tawajood.the_community_user.domain.repository.on_boarding.IOnBoardingRepository
import com.tawajood.the_community_user.utils.logE
import javax.inject.Inject

class SetOnBoardingShownUseCase @Inject constructor(
    private val onBoardingRepository: IOnBoardingRepository
) {
    suspend operator fun invoke() {
        try {
            onBoardingRepository.setOnBoardingShown()
        } catch (e: Exception) {
            "SetOnBoardingShownUseCase - $e".logE()
        }
    }
}