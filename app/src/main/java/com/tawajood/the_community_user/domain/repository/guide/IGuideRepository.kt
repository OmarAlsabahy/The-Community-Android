package com.tawajood.the_community_user.domain.repository.guide

import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.guide.GuideCategoryResponseDto
import com.tawajood.the_community_user.domain.models.guide.GuideResponseDto

interface IGuideRepository {
    suspend fun getGuideCategories(): RequestState<List<GuideCategoryResponseDto>>
    suspend fun getGuides(categoryId: Int?): RequestState<List<GuideResponseDto>>
    suspend fun getGuideSearch(query: String): RequestState<List<GuideResponseDto>>
}