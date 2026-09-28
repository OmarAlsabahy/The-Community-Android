package com.tawajood.the_community_user.app.ui.screens.home

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.home.AnnouncementModelDto
import com.tawajood.the_community_user.domain.models.home.BannerModel
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel
import com.tawajood.the_community_user.domain.usecase.home.GetAnnouncementsUseCase
import com.tawajood.the_community_user.domain.usecase.home.GetBannersUseCase
import com.tawajood.the_community_user.domain.usecase.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val profile: ProfileResponseModel? = null,
    val searchValue: String = "",
    val banners : List<BannerModel> = emptyList(),
    val announcement : AnnouncementModelDto? = null
): UiState
sealed interface HomeProfileIntent: UiIntent{
    data class OnSearchValueChanges(val value: String): HomeProfileIntent
}
sealed interface HomeProfileEffect: UiEffect{
    data class ShowToast(val message: String): HomeProfileEffect
}
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getBannersUseCase: GetBannersUseCase,
    private val getAnnouncementsUseCase: GetAnnouncementsUseCase
): BaseViewModel<HomeUiState, HomeProfileIntent, HomeProfileEffect>(HomeUiState()) {
    init {
        loadPage()
    }

    private fun loadPage() {
        getProfile()
        getBanners()
        getAnnouncement()
    }

    private fun getAnnouncement() {
        launchScope {
            getAnnouncementsUseCase(Unit).collect { state->
                when(state){
                    is RequestState.Success->{
                        setState {
                            copy(
                                announcement = state.data
                            )
                        }
                    }
                    is RequestState.Error->{
                        emitEffect {
                            HomeProfileEffect.ShowToast(state.message)
                        }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getBanners() {
        launchScope {
            getBannersUseCase(Unit).collect { state->
                when(state){
                    is RequestState.Success->{
                        setState {
                            copy(
                                banners = state.data
                            )
                        }
                    }
                    is RequestState.Error->{
                        emitEffect { HomeProfileEffect.ShowToast(state.message) }
                    }
                    is RequestState.Loading->{
                        setState {
                            copy(
                                isLoading = true
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getProfile() {
        launchScope {
            getProfileUseCase(Unit).collect { state ->
                when (state) {
                    is RequestState.Loading -> {

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
                        emitEffect { HomeProfileEffect.ShowToast(state.message) }
                    }
                }
            }
        }
    }

    override suspend fun handleIntent(intent: HomeProfileIntent) {
        when(intent){
            is HomeProfileIntent.OnSearchValueChanges->{
                setState {
                    copy(
                        searchValue = intent.value
                    )
                }
            }
        }
    }

}