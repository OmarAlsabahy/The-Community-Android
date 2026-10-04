package com.tawajood.the_community_user.domain.models.guide

import com.squareup.moshi.Json

data class GuideResponseDto(
    val id: Int?,
    val title: String?,
    val image: String?,
    @Json(name = "country_code")
    val countryCode: String?,
    val phone: String?,
    val category: GuideCategoryResponseDto,
    val address: String?,
    val location:Location
)
data class Location(
    @Json(name = "latitude")
    val lat: String?,
    @Json(name = "longitude")
    val long: String?
)