package com.tawajood.the_community_user.app.base

import com.tawajood.the_community_user.domain.base.NetworkError

data class DataState<T>(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val error: NetworkError? = null,
    val isSuccess: Boolean = false,
    val model: T? = null
)

fun DataState<*>.isLoadingState() = isSuccess.not() && isError.not() && isLoading

fun DataState<*>.isSuccessState() = isSuccess

fun DataState<*>.isErrorState() = isError
