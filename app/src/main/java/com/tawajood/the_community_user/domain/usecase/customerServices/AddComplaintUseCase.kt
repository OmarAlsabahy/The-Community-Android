package com.tawajood.the_community_user.domain.usecase.customerServices

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.customerServices.AddComplaintRequest
import com.tawajood.the_community_user.domain.repository.customerServices.ICustomerServiceRepository
import javax.inject.Inject

class AddComplaintUseCase @Inject constructor(private val repository: ICustomerServiceRepository):
    BaseUseCase<AddComplaintRequest , Any>() {
    override suspend fun execute(params: AddComplaintRequest): RequestState<Any> = repository.addComplaint(params)
}