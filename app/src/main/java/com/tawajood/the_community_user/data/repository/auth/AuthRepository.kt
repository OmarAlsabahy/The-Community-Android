package com.tawajood.the_community_user.data.repository.auth

import com.tawajood.the_community_user.data.base.BaseRepository
import com.tawajood.the_community_user.data.base.mapSuccess
import com.tawajood.the_community_user.data.remote.ApiService
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.auth.LoginRequest
import com.tawajood.the_community_user.domain.repository.auth.IAuthRepository
import javax.inject.Inject

class AuthRepository @Inject constructor(private val api: ApiService): IAuthRepository,
    BaseRepository() {
    override suspend fun login(request: LoginRequest): RequestState<String?> = wrapApi {
        api.login(request)
    }.mapSuccess {
        it.token
    }
}