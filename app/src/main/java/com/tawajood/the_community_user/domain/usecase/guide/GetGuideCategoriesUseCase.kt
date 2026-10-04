package com.tawajood.the_community_user.domain.usecase.guide

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.guide.GuideCategoryResponseDto
import com.tawajood.the_community_user.domain.repository.guide.IGuideRepository
import javax.inject.Inject

class GetGuideCategoriesUseCase @Inject constructor(private val repository: IGuideRepository):
    BaseUseCase<Unit, List<GuideCategoryResponseDto>>() {
    override suspend fun execute(params: Unit): RequestState<List<GuideCategoryResponseDto>>
    = repository.getGuideCategories()
}