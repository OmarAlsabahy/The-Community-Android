package com.tawajood.the_community_user.domain.usecase.society

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.repository.society.ISocietyHomeRepository
import javax.inject.Inject

class SaveRemovePostUseCase @Inject constructor(private val repository: ISocietyHomeRepository):
    BaseUseCase<Int, Any?>() {
    override suspend fun execute(params: Int): RequestState<Any?> = repository.saveRemovePost(params)
}