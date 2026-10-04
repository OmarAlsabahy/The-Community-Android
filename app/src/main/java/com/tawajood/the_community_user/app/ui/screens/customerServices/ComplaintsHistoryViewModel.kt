package com.tawajood.the_community_user.app.ui.screens.customerServices

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.customerServices.ComplaintResponseDto
import com.tawajood.the_community_user.domain.usecase.customerServices.GetComplaintsHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
data class ComplaintsHistoryUiState(
    val isLoading : Boolean = false,
    val complaintsHistory: List<ComplaintResponseDto> = emptyList(),
): UiState
sealed interface ComplaintHistoryIntent: UiIntent
sealed interface ComplaintHistoryUiEffect: UiEffect
@HiltViewModel
class ComplaintsHistoryViewModel @Inject constructor(
    private val getComplaintsHistoryUseCase: GetComplaintsHistoryUseCase
): BaseViewModel<ComplaintsHistoryUiState,ComplaintHistoryIntent,ComplaintHistoryUiEffect>(ComplaintsHistoryUiState()) {
    init {
        loadPage()
    }

    private fun loadPage() {
        getComplaints()
    }

    private fun getComplaints() {
        launchScope {
            getComplaintsHistoryUseCase(Unit).collect { state->
                when(state){
                    is RequestState.Loading->{
                        setState {
                            copy(
                                isLoading = true
                            )
                        }
                    }
                    is RequestState.Success->{
                        setState {
                            copy(
                                isLoading = false,
                                complaintsHistory = state.data
                            )
                        }
                    }
                    is RequestState.Error->{
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    override suspend fun handleIntent(intent: ComplaintHistoryIntent) {
        TODO("Not yet implemented")
    }
}