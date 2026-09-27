package com.tawajood.the_community_user.domain.usecase.home

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.home.BannerModel
import com.tawajood.the_community_user.domain.repository.home.IHomeRepository
import javax.inject.Inject

class GetBannersUseCase @Inject constructor(private val repository: IHomeRepository)
    : BaseUseCase<Unit, List<BannerModel>>() {
    override suspend fun execute(params: Unit): RequestState<List<BannerModel>> = repository.getBanners()
}