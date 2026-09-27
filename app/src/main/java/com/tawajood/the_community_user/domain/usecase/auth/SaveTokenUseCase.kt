package com.tawajood.the_community_user.domain.usecase.auth

import com.tawajood.the_community_user.data.data_store.IDataStorePreferences
import javax.inject.Inject

class SaveTokenUseCase @Inject constructor(private val dataPreference: IDataStorePreferences) {
    suspend fun saveToken(token: String){
        dataPreference.setUserToken(token)
    }
}