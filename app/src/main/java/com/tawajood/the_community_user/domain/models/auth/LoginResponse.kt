package com.tawajood.the_community_user.domain.models.auth

data class LoginResponse(
    val country_code: String?,
    val created_at: String?,
    val email: String?,
    val email_verified_at: String?,
    val fcm_token: String?,
    val id: Int?,
    val image: String?,
    val is_active: Int?,
    val locale: String?,
    val mobile_id: Int?,
    val name: String?,
    val notify_status: Int?,
    val phone: String?,
    val token: String?,
    val unit_num: String?,
    val updated_at: String?
)