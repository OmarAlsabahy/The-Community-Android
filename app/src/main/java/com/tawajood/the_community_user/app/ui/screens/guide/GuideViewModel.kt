package com.tawajood.the_community_user.app.ui.screens.guide

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.guide.GuideCategoryResponseDto
import com.tawajood.the_community_user.domain.models.guide.GuideResponseDto
import com.tawajood.the_community_user.domain.usecase.guide.GetGuideCategoriesUseCase
import com.tawajood.the_community_user.domain.usecase.guide.GetGuidesUseCase
import com.tawajood.the_community_user.domain.usecase.guide.GuideSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class GuideUiState(
    val isLoading: Boolean=false,
    val categories: List<GuideCategoryResponseDto> = emptyList(),
    val searchValue: String = "",
    val selectedCategory : GuideCategoryResponseDto? = null,
    val guides : List<GuideResponseDto> = emptyList()
): UiState
sealed interface GuideIntent: UiIntent{
    data class OnSearchValueChanges(val value:String): GuideIntent
    data class OnCategoryClicked(val category: GuideCategoryResponseDto): GuideIntent
    data object OnSearch: GuideIntent
}
sealed interface GuideEffect: UiEffect{
    data class ShowToast(val message: String): GuideEffect
}
@HiltViewModel
class GuideViewModel @Inject constructor(
    private val getGuideCategoriesUseCase: GetGuideCategoriesUseCase,
    private val getGuidesUseCase: GetGuidesUseCase,
    private val getSearchedGuides: GuideSearchUseCase
)
    : BaseViewModel<GuideUiState,GuideIntent,GuideEffect>(GuideUiState()){
        init {
            loadPage()
        }

    private fun loadPage() {
        getGuideCategories()
        getGuides(null)
    }
    private fun getGuides(categoryId:Int?){
        launchScope {
            getGuidesUseCase(categoryId).collect { state->
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
                                guides = state.data
                            )
                        }
                    }
                    is RequestState.Error->{
                        setState {
                            copy(
                                isLoading = false,
                            )
                        }
                        emitEffect { GuideEffect.ShowToast(state.message) }
                    }
                }
            }
        }
    }

    private fun getGuideCategories() {
        launchScope {
            getGuideCategoriesUseCase(Unit).collect { state->
                when(state) {
                    is RequestState.Loading -> {
                    }
                    is RequestState.Success -> {
                        setState { copy(categories = state.data) }
                    }
                    is RequestState.Error -> {
                        emitEffect { GuideEffect.ShowToast(state.message) }
                    }
                }
            }
        }
    }

    override suspend fun handleIntent(intent: GuideIntent) {
        val currentState = getCurrentState()
        when(intent){
            is GuideIntent.OnSearchValueChanges->{
                setState {
                    copy(
                        searchValue = intent.value
                    )
                }
            }
            is GuideIntent.OnCategoryClicked->{
                if (intent.category!=currentState.selectedCategory){
                    setState {
                        copy(selectedCategory = intent.category)
                    }
                    getGuides(intent.category.id)
                }else{
                    setState {
                        copy(selectedCategory = null)
                    }
                    getGuides(null)
                }
            }
            is GuideIntent.OnSearch->{
                search(currentState.searchValue.trim())
            }
        }
    }

    private fun search(query: String) {
        if (query.isNotEmpty()){
            launchScope {
                getSearchedGuides(query).collect { state->
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
                                copy(isLoading = false, guides = state.data)
                            }
                        }
                        is RequestState.Error->{
                            setState {
                                copy(isLoading = false)
                            }
                            emitEffect { GuideEffect.ShowToast(state.message) }
                        }
                    }
                }
            }
        }
    }
}