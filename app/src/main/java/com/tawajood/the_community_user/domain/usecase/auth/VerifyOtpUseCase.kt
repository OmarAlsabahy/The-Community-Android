package com.tawajood.the_community_user.domain.usecase.auth

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.auth.VerifyOtpResponseDto
import com.tawajood.the_community_user.domain.repository.auth.IAuthRepository
import javax.inject.Inject

class VerifyOtpUseCase @Inject constructor(private val repository: IAuthRepository)
    : BaseUseCase<VerifyOtpUseCase.VerifyOtpRequest, VerifyOtpResponseDto>() {
    override suspend fun execute(params: VerifyOtpRequest): RequestState<VerifyOtpResponseDto>
    = repository.verifyOtp(params.phone,params.code)

    data class VerifyOtpRequest(
        val phone: String,
        val code: String
    )
}