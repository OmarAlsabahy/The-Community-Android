package com.tawajood.the_community_user.domain.models.auth

import com.squareup.moshi.Json

data class VerifyOtpResponseDto(
    val message: String?,
    @Json(name = "reset_token")
    val resetToken: String?
)