package com.tawajood.the_community_user.data.repository.profile

import com.tawajood.the_community_user.data.base.BaseRepository
import com.tawajood.the_community_user.data.base.mapSuccess
import com.tawajood.the_community_user.data.remote.ApiService
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.mapper.ProfileMapper
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel
import com.tawajood.the_community_user.domain.repository.profile.IProfileRepository
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: ApiService,
    private val mapper: ProfileMapper
): IProfileRepository, BaseRepository() {
    override suspend fun getProfileRepository(): RequestState<ProfileResponseModel> = wrapApi {
        api.getProfile()
    }.mapSuccess {
        mapper.map(it)
    }
}