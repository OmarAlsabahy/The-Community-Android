package com.tawajood.the_community_user.domain.mapper

import com.tawajood.the_community_user.data.base.Mapper
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseDto
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel
import javax.inject.Inject

class ProfileMapper @Inject constructor(): Mapper<ProfileResponseDto, ProfileResponseModel> {
    override fun map(input: ProfileResponseDto): ProfileResponseModel = ProfileResponseModel(
        name = input.name,
        phone = input.phone,
        countryCode = input.country_code,
        image = input.image
    )
}