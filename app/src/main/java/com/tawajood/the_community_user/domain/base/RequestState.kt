package com.tawajood.the_community_user.domain.base

sealed class RequestState<out T> {

    data object Loading : RequestState<Nothing>()

    data class Success<out T>(val data: T) : RequestState<T>()

    data class Error(
        val message: String,
        val exception: Throwable? = null,
        val networkError: NetworkError? = null
    ) : RequestState<Nothing>()
}
