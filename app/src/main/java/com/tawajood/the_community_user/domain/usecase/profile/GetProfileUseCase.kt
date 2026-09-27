package com.tawajood.the_community_user.domain.usecase.profile

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel
import com.tawajood.the_community_user.domain.repository.profile.IProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: IProfileRepository
): BaseUseCase<Unit, ProfileResponseModel>() {
    override suspend fun execute(params: Unit): RequestState<ProfileResponseModel> = repository.getProfileRepository()
}