package com.tawajood.the_community_user.domain.repository.customerServices

import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.customerServices.AddComplaintRequest
import com.tawajood.the_community_user.domain.models.customerServices.ComplaintResponseDto
import com.tawajood.the_community_user.domain.models.customerServices.CustomerServicesCategoryResponseDto
import com.tawajood.the_community_user.domain.models.customerServices.MaintenanceCategoryDto

interface ICustomerServiceRepository {
    suspend fun getCustomerServiceCategories(): RequestState<List<CustomerServicesCategoryResponseDto>>
    suspend fun addComplaint(request: AddComplaintRequest): RequestState<Any>
    suspend fun getComplaintsHistory(): RequestState<List<ComplaintResponseDto>>
    suspend fun getMaintenanceCategories(): RequestState<List<MaintenanceCategoryDto>>
}