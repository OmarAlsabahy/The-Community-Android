package com.tawajood.the_community_user.data.repository.auth

import com.tawajood.the_community_user.data.base.BaseRepository
import com.tawajood.the_community_user.data.base.mapSuccess
import com.tawajood.the_community_user.data.remote.ApiService
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.auth.LoginRequest
import com.tawajood.the_community_user.domain.models.auth.VerifyOtpResponseDto
import com.tawajood.the_community_user.domain.repository.auth.IAuthRepository
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: ApiService): IAuthRepository,
    BaseRepository() {
    override suspend fun login(request: LoginRequest): RequestState<String?> = wrapApi {
        api.login(request)
    }.mapSuccess {
        it.token
    }

    override suspend fun forgetPassword(phone: String): RequestState<Any> = wrapApi {
        api.forgetPassword(phone)
    }

    override suspend fun verifyOtp(
        phone: String,
        code: String
    ): RequestState<VerifyOtpResponseDto> = wrapApi {
        api.verifyOtp(phone,code)
    }

    override suspend fun resetPassword(
        resetToken: String,
        password: String,
        confirmPassword: String,
        phone: String
    ): RequestState<Any> = wrapApi {
        api.resetPassword(resetToken,password,confirmPassword,phone)
    }
}