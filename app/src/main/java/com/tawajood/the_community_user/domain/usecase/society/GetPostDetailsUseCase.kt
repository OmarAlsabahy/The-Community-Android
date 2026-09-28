package com.tawajood.the_community_user.domain.usecase.society

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.repository.society.ISocietyHomeRepository
import javax.inject.Inject

class GetPostDetailsUseCase @Inject constructor(private val repository: ISocietyHomeRepository):
    BaseUseCase<Int, PostDto>() {
    override suspend fun execute(params: Int): RequestState<PostDto> = repository.getPostDetails(params)
}