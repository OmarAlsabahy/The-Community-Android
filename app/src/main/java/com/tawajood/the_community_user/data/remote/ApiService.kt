package com.tawajood.the_community_user.data.remote

import com.tawajood.the_community_user.data.base.BaseResponse
import com.tawajood.the_community_user.domain.models.auth.LoginRequest
import com.tawajood.the_community_user.domain.models.auth.LoginResponse
import com.tawajood.the_community_user.domain.models.home.AnnouncementModelDto
import com.tawajood.the_community_user.domain.models.home.BannerModel
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseDto
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.models.society.PostDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST(ApiPaths.login)
    suspend fun login(@Body request: LoginRequest): Response<BaseResponse<LoginResponse>>
    @GET(ApiPaths.profile)
    suspend fun getProfile(): Response<BaseResponse<ProfileResponseDto>>
    @GET(ApiPaths.banners)
    suspend fun getBanners(): Response<BaseResponse<List<BannerModel>>>
    @GET(ApiPaths.announcement)
    suspend fun getAnnouncements(): Response<BaseResponse<AnnouncementModelDto>>
    @GET(ApiPaths.postsCategories)
    suspend fun getPostCategories(): Response<BaseResponse<List<PostCategoriesDto>>>
    @GET(ApiPaths.posts)
    suspend fun getPosts(@Query("category_id") categoryId: Int?): Response<BaseResponse<List<PostDto>>>
    @POST(ApiPaths.changePostLike)
    suspend fun changePostLikeStatus(@Path("id")id: Int): Response<BaseResponse<Any>>
    @GET(ApiPaths.postDetails)
    suspend fun getPostDetails(@Path("id")id: Int): Response<BaseResponse<PostDto>>


}
