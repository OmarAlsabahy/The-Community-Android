package com.tawajood.the_community_user.domain.models.customerServices

import com.squareup.moshi.Json

data class ComplaintResponseDto(
    val id: Int?,
    @Json(name = "country_code")
    val countryCode: String?,
    val phone: String?,
    val description: String?,
    val address: String?,
    val category: CustomerServicesCategoryResponseDto?,
    val media : List<Media?>?
)
data class Media(
    val id: Int?,
    val url: String?,
    val type: String
)