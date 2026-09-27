package com.tawajood.the_community_user.domain.repository.profile

import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel

interface IProfileRepository {
    suspend fun getProfileRepository(): RequestState<ProfileResponseModel>
}