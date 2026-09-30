package com.tawajood.the_community_user.domain.usecase.auth

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.repository.auth.IAuthRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(private val repository: IAuthRepository)
    : BaseUseCase<ResetPasswordUseCase.ResetPasswordRequest, Any>(){
    override suspend fun execute(params: ResetPasswordRequest): RequestState<Any>
    = repository.resetPassword(params.resetToken,params.password,params.confirmPassword,params.phone)

    data class ResetPasswordRequest(
        val resetToken:String,
        val password: String,
        val confirmPassword: String,
        val phone: String
    )
}