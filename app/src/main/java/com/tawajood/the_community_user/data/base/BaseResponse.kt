package com.tawajood.the_community_user.data.base

data class BaseResponse<T>(
    val result: Boolean? = null,
    val errNum: Int? = null,
    val message: String? = null,
    val data: T? = null
)