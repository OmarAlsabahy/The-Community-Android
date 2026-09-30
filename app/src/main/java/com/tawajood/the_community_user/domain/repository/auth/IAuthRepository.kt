package com.tawajood.the_community_user.domain.repository.auth

import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.auth.LoginRequest
import com.tawajood.the_community_user.domain.models.auth.VerifyOtpResponseDto

interface IAuthRepository {
    suspend fun login(request: LoginRequest): RequestState<String?>
    suspend fun forgetPassword(phone: String): RequestState<Any>
    suspend fun verifyOtp(phone: String,code: String): RequestState<VerifyOtpResponseDto>
    suspend fun resetPassword(
        resetToken: String,
        password: String,
        confirmPassword: String,
        phone: String
    ): RequestState<Any>
}