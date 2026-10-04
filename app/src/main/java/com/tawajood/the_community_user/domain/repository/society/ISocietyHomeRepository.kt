package com.tawajood.the_community_user.domain.repository.society

import android.net.Uri
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.models.society.PostDto

interface ISocietyHomeRepository {
    suspend fun getPostsCategories(): RequestState<List<PostCategoriesDto>>
    suspend fun getPosts(categoryId: Int?): RequestState<List<PostDto>>
    suspend fun changePostLikeStatus(id: Int): RequestState<PostDto>
    suspend fun getPostDetails(id: Int): RequestState<PostDto>
    suspend fun addComment(postId: Int,comment: String): RequestState<Any?>
    suspend fun changeCommentLikeStatus(id: Int): RequestState<Any?>
    suspend fun saveRemovePost(id: Int): RequestState<Any?>
    suspend fun createPost(content: String,categoryId: Int,media: List<Uri>): RequestState<PostDto>
}