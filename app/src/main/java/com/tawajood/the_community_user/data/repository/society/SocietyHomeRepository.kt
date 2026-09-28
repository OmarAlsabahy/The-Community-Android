package com.tawajood.the_community_user.data.repository.society

import com.tawajood.the_community_user.data.base.BaseRepository
import com.tawajood.the_community_user.data.remote.ApiService
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.repository.society.ISocietyHomeRepository
import javax.inject.Inject

class SocietyHomeRepository @Inject constructor(private val api: ApiService): ISocietyHomeRepository,
    BaseRepository() {
    override suspend fun getPostsCategories(): RequestState<List<PostCategoriesDto>> = wrapApi {
        api.getPostCategories()
    }

    override suspend fun getPosts(categoryId: Int?): RequestState<List<PostDto>> = wrapApi {
        api.getPosts(categoryId)
    }

    override suspend fun changePostLikeStatus(id: Int): RequestState<Any> = wrapApi {
        api.changePostLikeStatus(id)
    }

    override suspend fun getPostDetails(id: Int): RequestState<PostDto> = wrapApi {
        api.getPostDetails(id)
    }
}