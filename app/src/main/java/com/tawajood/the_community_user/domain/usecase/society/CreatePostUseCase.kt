package com.tawajood.the_community_user.domain.usecase.society

import android.net.Uri
import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.repository.society.ISocietyHomeRepository
import javax.inject.Inject

class CreatePostUseCase @Inject constructor(private val repository: ISocietyHomeRepository):
    BaseUseCase<CreatePostUseCase.CreatePostRequest, PostDto>() {
    override suspend fun execute(params: CreatePostRequest): RequestState<PostDto>
    = repository.createPost(params.content,params.categoryId,params.media)

    data class CreatePostRequest(
        val content: String,
        val categoryId: Int,
        val media: List<Uri>
    )
}