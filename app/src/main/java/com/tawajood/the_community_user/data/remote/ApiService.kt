package com.tawajood.the_community_user.data.remote

import com.tawajood.the_community_user.data.base.BaseResponse
import com.tawajood.the_community_user.domain.models.auth.LoginRequest
import com.tawajood.the_community_user.domain.models.auth.LoginResponse
import com.tawajood.the_community_user.domain.models.home.BannerModel
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST(ApiPaths.login)
    suspend fun login(@Body request: LoginRequest): Response<BaseResponse<LoginResponse>>
    @GET(ApiPaths.profile)
    suspend fun getProfile(): Response<BaseResponse<ProfileResponseDto>>
    @GET(ApiPaths.banners)
    suspend fun getBanners(): Response<BaseResponse<List<BannerModel>>>
}
