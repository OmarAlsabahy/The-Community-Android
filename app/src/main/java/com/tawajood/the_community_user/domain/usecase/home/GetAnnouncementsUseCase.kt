package com.tawajood.the_community_user.domain.usecase.home

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.home.AnnouncementModelDto
import com.tawajood.the_community_user.domain.repository.home.IHomeRepository
import javax.inject.Inject

class GetAnnouncementsUseCase @Inject constructor(private val repository: IHomeRepository)
    : BaseUseCase<Unit, AnnouncementModelDto>(){
    override suspend fun execute(params: Unit): RequestState<AnnouncementModelDto>  = repository.getAnnouncements()
}