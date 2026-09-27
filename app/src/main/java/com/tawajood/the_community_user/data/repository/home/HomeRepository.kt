package com.tawajood.the_community_user.data.repository.home

import com.tawajood.the_community_user.data.base.BaseRepository
import com.tawajood.the_community_user.data.remote.ApiService
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.home.BannerModel
import com.tawajood.the_community_user.domain.repository.home.IHomeRepository
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiService: ApiService): IHomeRepository,
    BaseRepository() {
    override suspend fun getBanners(): RequestState<List<BannerModel>> = wrapApi {
        apiService.getBanners()
    }
}