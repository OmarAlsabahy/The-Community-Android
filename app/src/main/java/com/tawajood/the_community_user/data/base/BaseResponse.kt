package com.tawajood.the_community_user.data.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "result")
    val result: Boolean? = null,

    @Json(name = "errNum")
    val errNum: Int? = null,

    @Json(name = "message")
    val message: String? = null,

    @Json(name = "data")
    val data: T? = null
)