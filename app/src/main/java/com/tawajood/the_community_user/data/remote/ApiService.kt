package com.tawajood.the_community_user.data.remote

import com.tawajood.the_community_user.data.base.BaseResponse
import com.tawajood.the_community_user.domain.models.auth.LoginRequest
import com.tawajood.the_community_user.domain.models.auth.LoginResponse
import com.tawajood.the_community_user.domain.models.auth.VerifyOtpResponseDto
import com.tawajood.the_community_user.domain.models.customerServices.AddComplaintRequest
import com.tawajood.the_community_user.domain.models.customerServices.ComplaintResponseDto
import com.tawajood.the_community_user.domain.models.customerServices.CustomerServicesCategoryResponseDto
import com.tawajood.the_community_user.domain.models.customerServices.MaintenanceCategoryDto
import com.tawajood.the_community_user.domain.models.guide.GuideCategoryResponseDto
import com.tawajood.the_community_user.domain.models.guide.GuideResponseDto
import com.tawajood.the_community_user.domain.models.home.AnnouncementModelDto
import com.tawajood.the_community_user.domain.models.home.BannerModel
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseDto
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.models.society.PostDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
    suspend fun changePostLikeStatus(@Path("id")id: Int): Response<BaseResponse<PostDto>>
    @GET(ApiPaths.postDetails)
    suspend fun getPostDetails(@Path("id")id: Int): Response<BaseResponse<PostDto>>
    @POST(ApiPaths.comment)
    @FormUrlEncoded
    suspend fun addComment(@Field("post_id")id: Int,@Field("comment")comment: String): Response<BaseResponse<Any?>>
    @POST(ApiPaths.commentLike)
    suspend fun changeCommentLikeStatus(@Path("id")id: Int): Response<BaseResponse<Any?>>
    @POST(ApiPaths.save_remove_post)
    @FormUrlEncoded
    suspend fun saveRemovePost(@Field("post_id")id: Int): Response<BaseResponse<Any?>>
    @POST(ApiPaths.createPost)
    @Multipart
    suspend fun createPost(
        @Part("content") content: RequestBody,
        @Part("post_category_id") categoryId: RequestBody,
        @Part media: List<MultipartBody.Part>?
        ): Response<BaseResponse<PostDto>>
    @POST(ApiPaths.forgetPassword)
    @FormUrlEncoded
    suspend fun forgetPassword(@Field("phone")phone: String): Response<BaseResponse<Any>>
    @POST(ApiPaths.verifyOtp)
    @FormUrlEncoded
    suspend fun verifyOtp(
        @Field("phone")phone: String,
        @Field("code")code: String
    ):Response<BaseResponse<VerifyOtpResponseDto>>
    @POST(ApiPaths.resetPassword)
    @FormUrlEncoded
    suspend fun resetPassword(
        @Field("reset_token")resetToken: String,
        @Field("password")password: String,
        @Field("password_confirmation")passwordConfirmation: String,
        @Field("phone")phone: String
    ):Response<BaseResponse<Any>>
    @GET(ApiPaths.guideCategories)
    suspend fun getGuideCategories(): Response<BaseResponse<List<GuideCategoryResponseDto>>>
    @GET(ApiPaths.guides)
    suspend fun getGuides(@Query("category_id")categoryId: Int?): Response<BaseResponse<List<GuideResponseDto>>>
    @GET(ApiPaths.guideSearch)
    suspend fun guidSearch(@Query("text")query: String): Response<BaseResponse<List<GuideResponseDto>>>
    @GET(ApiPaths.customerServicesCategories)
    suspend fun getCustomerServicesCategories(): Response<BaseResponse<List<CustomerServicesCategoryResponseDto>>>
    
    @Multipart
    @POST(ApiPaths.addComplaint)
    suspend fun addComplaint(
        @Part("category_id") categoryId: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part("complaint") complaint: RequestBody,
        @Part("country_code") countryCode: RequestBody,
        @Part("description") description: RequestBody,
        @Part media: List<MultipartBody.Part>?
    ): Response<BaseResponse<Any>>
    @GET(ApiPaths.complaintsHistory)
    suspend fun getComplaints(): Response<BaseResponse<List<ComplaintResponseDto>>>
    @GET(ApiPaths.maintenanceCategories)
    suspend fun getMaintenanceCategories(): Response<BaseResponse<List<MaintenanceCategoryDto>>>
}
