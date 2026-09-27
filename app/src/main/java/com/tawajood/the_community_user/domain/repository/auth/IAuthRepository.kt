package com.tawajood.the_community_user.domain.repository.auth

import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.auth.LoginRequest

interface IAuthRepository {
    suspend fun login(request: LoginRequest): RequestState<String?>
}