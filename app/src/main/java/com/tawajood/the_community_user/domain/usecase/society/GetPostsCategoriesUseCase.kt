package com.tawajood.the_community_user.domain.usecase.society

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.repository.society.ISocietyHomeRepository
import javax.inject.Inject

class GetPostsCategoriesUseCase @Inject constructor(private val repository: ISocietyHomeRepository):
    BaseUseCase<Unit, List<PostCategoriesDto>>() {
    override suspend fun execute(params: Unit): RequestState<List<PostCategoriesDto>> = repository.getPostsCategories()
}