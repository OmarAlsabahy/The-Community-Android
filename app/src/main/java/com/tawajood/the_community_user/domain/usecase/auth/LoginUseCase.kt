package com.tawajood.the_community_user.domain.usecase.auth

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.auth.LoginRequest
import com.tawajood.the_community_user.domain.repository.auth.IAuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: IAuthRepository)
    : BaseUseCase<LoginRequest, String?>() {
    override suspend fun execute(params: LoginRequest): RequestState<String?> = repository.login(params)
}