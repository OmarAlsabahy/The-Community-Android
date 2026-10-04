package com.tawajood.the_community_user.domain.models.customerServices

import com.squareup.moshi.Json
import okhttp3.MultipartBody
import okhttp3.RequestBody

data class AddComplaintRequest(
    @Json(name = "category_id")
    val categoryId: RequestBody,
    @Json(name = "phone")
    val phone: RequestBody,
    val address: RequestBody,
    val complaint: RequestBody,
    @Json(name = "country_code")
    val countryCode: RequestBody,
    val description: RequestBody,
    val media: List<MultipartBody.Part>?
)