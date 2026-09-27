package com.tawajood.the_community_user.data.repository.on_boarding

import com.tawajood.the_community_user.data.data_store.IDataStorePreferences
import com.tawajood.the_community_user.domain.repository.on_boarding.IOnBoardingRepository
import javax.inject.Inject

class OnBoardingRepository @Inject constructor(
    private val dataStorePref: IDataStorePreferences
) : IOnBoardingRepository {

    override suspend fun setOnBoardingShown() {
        dataStorePref.setOnBoardingShown()
    }

    override suspend fun isOnBoardingShown(): Boolean {
        return dataStorePref.isOnBoardingShown()
    }
}