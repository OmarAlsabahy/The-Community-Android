package com.tawajood.the_community_user.app.ui.screens.more

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.more.MoreItems
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel
import com.tawajood.the_community_user.domain.usecase.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class MoreUiState(
    val isLoading: Boolean = false,
    val profile: ProfileResponseModel?=null,
    val personalInformationItems: List<MoreItems> = listOf(
        MoreItems.PersonalInfo,
        MoreItems.PostsHistory,
        MoreItems.ComplaintsHistory
    ),
    val settingsItems: List<MoreItems> = listOf(
        MoreItems.Favorites,
        MoreItems.Language,
        MoreItems.Notifications,
        MoreItems.ContactUs,
        MoreItems.TermsAndConditions
    ),
    val logoutItems: List<MoreItems> = listOf(
        MoreItems.Logout
    )
): UiState
sealed interface MoreIntent: UiIntent{
    data class OnItemClicked(val route: AppRoutes?): MoreIntent
}
sealed interface MoreEffect: UiEffect{
    data class Nav(val route: AppRoutes): MoreEffect
}
@HiltViewModel
class MoreViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
): BaseViewModel<MoreUiState,MoreIntent,MoreEffect>(MoreUiState()) {
    init {
        loadPage()
    }

    private fun loadPage() {
        getProfile()
    }

    private fun getProfile() {
        launchScope {
            getProfileUseCase(Unit).collect { state->
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
                                profile = state.data
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

    override suspend fun handleIntent(intent: MoreIntent) {
        when(intent){
            is MoreIntent.OnItemClicked->{
                if (intent.route!=null) {
                    emitEffect {
                        MoreEffect.Nav(intent.route)
                    }
                }
            }
        }
    }
}