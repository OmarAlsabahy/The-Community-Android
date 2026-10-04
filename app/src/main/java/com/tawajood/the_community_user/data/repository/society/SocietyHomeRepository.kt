package com.tawajood.the_community_user.data.repository.society

import android.content.Context
import android.net.Uri
import com.tawajood.the_community_user.data.base.BaseRepository
import com.tawajood.the_community_user.data.remote.ApiService
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.repository.society.ISocietyHomeRepository
import com.tawajood.the_community_user.utils.FileUtils
import com.tawajood.the_community_user.utils.compressImageToSize
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class SocietyHomeRepository @Inject constructor(
    private val api: ApiService,
    @ApplicationContext private val context: Context
): ISocietyHomeRepository,
    BaseRepository() {
    override suspend fun getPostsCategories(): RequestState<List<PostCategoriesDto>> = wrapApi {
        api.getPostCategories()
    }

    override suspend fun getPosts(categoryId: Int?): RequestState<List<PostDto>> = wrapApi {
        api.getPosts(categoryId)
    }

    override suspend fun changePostLikeStatus(id: Int): RequestState<PostDto> = wrapApi {
        api.changePostLikeStatus(id)
    }

    override suspend fun getPostDetails(id: Int): RequestState<PostDto> = wrapApi {
        api.getPostDetails(id)
    }

    override suspend fun addComment(
        postId: Int,
        comment: String
    ): RequestState<Any?> = wrapApi {
        api.addComment(postId,comment)
    }

    override suspend fun changeCommentLikeStatus(id: Int): RequestState<Any?> = wrapApi {
        api.changeCommentLikeStatus(id)
    }

    override suspend fun saveRemovePost(id: Int): RequestState<Any?> = wrapApi {
        api.saveRemovePost(id)
    }

    override suspend fun createPost(
        content: String,
        categoryId: Int,
        media: List<Uri>
    ): RequestState<PostDto> {
        val multiPartMedia : MutableList<MultipartBody.Part> = mutableListOf()
        media.forEach {
            val file = FileUtils.convertUriToFile(context , it)
            val compressedFile = compressImageToSize(file!!)
            val multiPartFile = FileUtils.convertFileToMultiPart("media[]",compressedFile)
            multiPartMedia.add(multiPartFile)
        }
        return wrapApi {
            api.createPost(content.toRequestBody(),categoryId.toString().toRequestBody()
                ,if (multiPartMedia.isNotEmpty()) multiPartMedia else null)
        }
    }
}