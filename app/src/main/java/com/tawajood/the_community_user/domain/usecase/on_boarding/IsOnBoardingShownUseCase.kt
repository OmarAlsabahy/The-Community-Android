package com.tawajood.the_community_user.domain.usecase.on_boarding

import com.tawajood.the_community_user.domain.repository.on_boarding.IOnBoardingRepository
import com.tawajood.the_community_user.utils.logE
import javax.inject.Inject

class IsOnBoardingShownUseCase @Inject constructor(
    private val onBoardingRepository: IOnBoardingRepository
) {
    suspend operator fun invoke(): Boolean {
        try {
            return onBoardingRepository.isOnBoardingShown()
        } catch (e: Exception) {
            "IsOnBoardingShownUseCase - $e".logE()
            return true
        }
    }
}