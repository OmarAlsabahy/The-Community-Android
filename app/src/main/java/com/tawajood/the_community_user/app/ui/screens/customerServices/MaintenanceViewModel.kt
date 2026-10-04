package com.tawajood.the_community_user.app.ui.screens.customerServices

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.customerServices.MaintenanceCategoryDto
import com.tawajood.the_community_user.domain.usecase.customerServices.GetMaintenanceCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class MaintenanceUiState(
    val isLoading: Boolean = false,
    val categories: List<MaintenanceCategoryDto> = emptyList(),
    val selectedCategory:MaintenanceCategoryDto? = null
): UiState
sealed interface MaintenanceIntent: UiIntent{
    data class OnCategorySelected(val category: MaintenanceCategoryDto): MaintenanceIntent
}
sealed interface MaintenanceEffect: UiEffect{
    data class ShowToast(val message: String): MaintenanceEffect
}
@HiltViewModel
class MaintenanceViewModel @Inject constructor(
    private val getMaintenanceCategoriesUseCase: GetMaintenanceCategoriesUseCase
): BaseViewModel<MaintenanceUiState,MaintenanceIntent, MaintenanceEffect>(MaintenanceUiState()) {
    init {
        loadPage()
    }

    private fun loadPage() {
        getCategories()
    }

    private fun getCategories() {
        launchScope {
            getMaintenanceCategoriesUseCase(Unit).collect { state->
                when(state){
                    is RequestState.Success->{
                        setState {
                            copy(
                                isLoading = false,
                                categories = state.data
                            )
                        }
                    }
                    is RequestState.Error->{
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                        emitEffect { MaintenanceEffect.ShowToast(state.message) }
                    }
                    else -> {}
                }
            }
        }
    }

    override suspend fun handleIntent(intent: MaintenanceIntent) {
        val currentState = getCurrentState()
        when(intent){
            is MaintenanceIntent.OnCategorySelected->{
                if (currentState.selectedCategory != intent.category){
                    setState {
                        copy(
                            selectedCategory = intent.category
                        )
                    }
                }else{
                    setState {
                        copy(
                            selectedCategory = null
                        )
                    }
                }
            }
        }
    }
}