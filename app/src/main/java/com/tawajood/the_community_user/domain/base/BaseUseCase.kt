package com.tawajood.the_community_user.domain.base

import com.tawajood.the_community_user.utils.logE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseUseCase<in Params, out T> {

    abstract suspend fun execute(params: Params): RequestState<T>

    operator fun invoke(params: Params): Flow<RequestState<T>> = flow {
        emit(RequestState.Loading)
        try {
            val result = execute(params)
            emit(result)
        } catch (e: Exception) {
            "Error in UseCase: ${e.stackTraceToString()}".logE()
            emit(RequestState.Error("Unknown error", e, NetworkError.UNKNOWN))
        }
    }
}