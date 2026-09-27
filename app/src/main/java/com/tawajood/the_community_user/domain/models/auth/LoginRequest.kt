package com.tawajood.the_community_user.domain.models.auth

import com.squareup.moshi.Json

data class LoginRequest(
    val password: String,
    val phone: String
)