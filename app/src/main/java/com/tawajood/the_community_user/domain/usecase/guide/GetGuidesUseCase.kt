package com.tawajood.the_community_user.domain.usecase.guide

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.guide.GuideResponseDto
import com.tawajood.the_community_user.domain.repository.guide.IGuideRepository
import javax.inject.Inject

class GetGuidesUseCase @Inject constructor(private val repository: IGuideRepository)
    : BaseUseCase<Int?, List<GuideResponseDto>>(){
    override suspend fun execute(params: Int?): RequestState<List<GuideResponseDto>>
    = repository.getGuides(params)
}