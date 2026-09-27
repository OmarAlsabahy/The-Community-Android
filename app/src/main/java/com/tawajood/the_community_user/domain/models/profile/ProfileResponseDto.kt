package com.tawajood.the_community_user.domain.models.profile

data class ProfileResponseDto(
    val community_notify: Int?,
    val community_service_notify: Int?,
    val country_code: String?,
    val created_at: String?,
    val email: String?,
    val email_verified_at: String?,
    val fcm_token: String?,
    val guide_notify: Int?,
    val id: Int?,
    val image: String?,
    val is_active: Int?,
    val locale: String?,
    val mobile_id: Int?,
    val name: String?,
    val notify_status: Int?,
    val permit_notify: Int?,
    val phone: String?,
    val service_notify: Int?,
    val unit_id: Int?,
    val updated_at: String?,
    val wallet: Double?
)