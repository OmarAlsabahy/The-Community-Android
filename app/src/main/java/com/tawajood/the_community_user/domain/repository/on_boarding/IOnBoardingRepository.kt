package com.tawajood.the_community_user.domain.repository.on_boarding

interface IOnBoardingRepository {
    suspend fun setOnBoardingShown()
    suspend fun isOnBoardingShown(): Boolean
}