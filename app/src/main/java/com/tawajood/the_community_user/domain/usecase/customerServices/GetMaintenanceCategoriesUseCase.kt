package com.tawajood.the_community_user.domain.usecase.customerServices

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.customerServices.MaintenanceCategoryDto
import com.tawajood.the_community_user.domain.repository.customerServices.ICustomerServiceRepository
import javax.inject.Inject

class GetMaintenanceCategoriesUseCase @Inject constructor(private val repository: ICustomerServiceRepository)
    : BaseUseCase<Unit, List<MaintenanceCategoryDto>>(){
    override suspend fun execute(params: Unit): RequestState<List<MaintenanceCategoryDto>>
    = repository.getMaintenanceCategories()
}