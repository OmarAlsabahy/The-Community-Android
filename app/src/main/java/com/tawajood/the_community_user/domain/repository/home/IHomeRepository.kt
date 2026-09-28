package com.tawajood.the_community_user.domain.repository.home

import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.home.AnnouncementModelDto
import com.tawajood.the_community_user.domain.models.home.BannerModel

interface IHomeRepository {
    suspend fun getBanners(): RequestState<List<BannerModel>>
    suspend fun getAnnouncements(): RequestState<AnnouncementModelDto>
}