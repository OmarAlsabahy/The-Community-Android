package com.tawajood.the_community_user.data.repository.customerServices

import com.tawajood.the_community_user.data.base.BaseRepository
import com.tawajood.the_community_user.data.remote.ApiService
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.customerServices.AddComplaintRequest
import com.tawajood.the_community_user.domain.models.customerServices.ComplaintResponseDto
import com.tawajood.the_community_user.domain.models.customerServices.CustomerServicesCategoryResponseDto
import com.tawajood.the_community_user.domain.models.customerServices.MaintenanceCategoryDto
import com.tawajood.the_community_user.domain.repository.customerServices.ICustomerServiceRepository
import javax.inject.Inject

class CustomerServicesRepository @Inject constructor(private val api: ApiService):
    ICustomerServiceRepository, BaseRepository() {
    override suspend fun getCustomerServiceCategories(): RequestState<List<CustomerServicesCategoryResponseDto>>
    = wrapApi {
        api.getCustomerServicesCategories()
    }

    override suspend fun addComplaint(request: AddComplaintRequest): RequestState<Any> = wrapApi {
        api.addComplaint(
            categoryId = request.categoryId,
            phone = request.phone,
            address = request.address,
            complaint = request.complaint,
            countryCode = request.countryCode,
            description = request.description,
            media = request.media
        )
    }

    override suspend fun getComplaintsHistory(): RequestState<List<ComplaintResponseDto>> = wrapApi {
        api.getComplaints()
    }

    override suspend fun getMaintenanceCategories(): RequestState<List<MaintenanceCategoryDto>> = wrapApi {
        api.getMaintenanceCategories()
    }

}