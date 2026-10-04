package com.tawajood.the_community_user.data.repository.guide

import com.tawajood.the_community_user.data.base.BaseRepository
import com.tawajood.the_community_user.data.remote.ApiService
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.guide.GuideCategoryResponseDto
import com.tawajood.the_community_user.domain.models.guide.GuideResponseDto
import com.tawajood.the_community_user.domain.repository.guide.IGuideRepository
import javax.inject.Inject

class GuideRepository @Inject constructor(private val api: ApiService): IGuideRepository,
    BaseRepository() {
    override suspend fun getGuideCategories(): RequestState<List<GuideCategoryResponseDto>> =
        wrapApi {
            api.getGuideCategories()
        }

    override suspend fun getGuides(categoryId: Int?): RequestState<List<GuideResponseDto>> = wrapApi {
        api.getGuides(categoryId)
    }

    override suspend fun getGuideSearch(query: String): RequestState<List<GuideResponseDto>> = wrapApi {
        api.guidSearch(query)
    }
}