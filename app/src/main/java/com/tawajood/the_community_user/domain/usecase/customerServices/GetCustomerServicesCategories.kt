package com.tawajood.the_community_user.domain.usecase.customerServices

import com.tawajood.the_community_user.domain.base.BaseUseCase
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.customerServices.CustomerServicesCategoryResponseDto
import com.tawajood.the_community_user.domain.repository.customerServices.ICustomerServiceRepository
import javax.inject.Inject

class GetCustomerServicesCategories @Inject constructor(private val repository: ICustomerServiceRepository)
    : BaseUseCase<Unit, List<CustomerServicesCategoryResponseDto>>(){
    override suspend fun execute(params: Unit): RequestState<List<CustomerServicesCategoryResponseDto>>
    = repository.getCustomerServiceCategories()
}