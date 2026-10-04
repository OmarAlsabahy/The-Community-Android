package com.tawajood.the_community_user.app.ui.screens.customerServices

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.customerServices.CustomerServicesCategoryResponseDto
import com.tawajood.the_community_user.domain.usecase.customerServices.GetCustomerServicesCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class CustomerServicesUiState(
    val isLoading : Boolean = false,
    val categories: List<CustomerServicesCategoryResponseDto> = emptyList()
): UiState
sealed interface CustomerServicesIntent: UiIntent{
    data class OnCategoryClicked(val id: Int?): CustomerServicesIntent
}
sealed interface CustomerServicesEffect: UiEffect{
    data class ShowToast(val message: String): CustomerServicesEffect
    data class Nav(val route: AppRoutes): CustomerServicesEffect
}
@HiltViewModel
class CustomerServicesViewModel @Inject constructor(
    private val getCategories: GetCustomerServicesCategories
): BaseViewModel<CustomerServicesUiState,CustomerServicesIntent,CustomerServicesEffect>(CustomerServicesUiState()) {
    init {
        loadPage()
    }

    private fun loadPage() {
        getCategories()
    }

    private fun getCategories() {
        launchScope {
            getCategories(Unit).collect { state->
                when(state){
                    is RequestState.Success->{
                        setState { copy(categories = state.data) }
                    }
                    is RequestState.Error->{
                        emitEffect { CustomerServicesEffect.ShowToast(state.message) }
                    }else -> {}
                }
            }
        }
    }

    override suspend fun handleIntent(intent: CustomerServicesIntent) {
        when(intent){
            is CustomerServicesIntent.OnCategoryClicked->{
                if (intent.id!=null){
                    emitEffect { CustomerServicesEffect.Nav(AppRoutes.AddComplaint(intent.id)) }
                }
            }
        }
    }
}