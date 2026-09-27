package com.tawajood.the_community_user.data.base

import com.tawajood.the_community_user.domain.base.RequestState

interface Mapper<I, O> {
    fun map(input: I): O
}

inline fun <T, R> RequestState<T>.mapSuccess(mapper: (T) -> R): RequestState<R> {
    return when (this) {
        is RequestState.Success -> RequestState.Success(mapper(data))
        is RequestState.Error -> RequestState.Error(message, exception, networkError)
        is RequestState.Loading -> RequestState.Loading
    }
}
