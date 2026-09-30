package com.tawajood.the_community_user.domain.usecase.auth

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.repository.auth.IAuthRepository
import javax.inject.Inject

class ForgetPasswordUseCase @Inject constructor(private val repository: IAuthRepository)
    : BaseUseCase<String, Any>(){
    override suspend fun execute(params: String): RequestState<Any> = repository.forgetPassword(params)
}