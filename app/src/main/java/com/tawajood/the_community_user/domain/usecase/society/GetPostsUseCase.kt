package com.tawajood.the_community_user.domain.usecase.society

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.repository.society.ISocietyHomeRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val repository: ISocietyHomeRepository):
    BaseUseCase<Int?, List<PostDto>>() {
    override suspend fun execute(params: Int?): RequestState<List<PostDto>> = repository.getPosts(params)
}