package com.tawajood.the_community_user.app.ui.screens.society

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.usecase.society.ChangePostLikeStatusUseCase
import com.tawajood.the_community_user.domain.usecase.society.GetPostsCategoriesUseCase
import com.tawajood.the_community_user.domain.usecase.society.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class SocietyHomeUiState(
    val isLoading: Boolean = false,
    val categories: List<PostCategoriesDto> = emptyList(),
    val selectedCategory : Int = -1,
    val posts: List<PostDto> = emptyList()
): UiState
sealed interface SocietyHomeIntent: UiIntent{
    data class ChangeCategory(val index: Int): SocietyHomeIntent
    data class ChangePostLike(val id: Int?): SocietyHomeIntent
    data class OnPostClicked(val id: Int?): SocietyHomeIntent
}
sealed interface SocietyHomeEffect: UiEffect{
    data class ShowToast(val message: String): SocietyHomeEffect
    data class Nav(val route: AppRoutes): SocietyHomeEffect
}
@HiltViewModel
class SocietyHomeViewModel @Inject constructor(
    private val getPostsCategoriesUseCase: GetPostsCategoriesUseCase,
    private val getPostsUseCase: GetPostsUseCase,
    private val changePostLikeStatusUseCase: ChangePostLikeStatusUseCase
): BaseViewModel<SocietyHomeUiState,SocietyHomeIntent,SocietyHomeEffect>(SocietyHomeUiState()) {
    init {
        loadPage()
    }

    private fun loadPage() {
        getCategories()
        getPosts(null)
    }

    private fun getPosts(categoryId: Int?) {
        launchScope {
            getPostsUseCase(categoryId).collect { state->
                when(state){
                    is RequestState.Loading->{
                        setState { copy(isLoading = true) }
                    }
                    is RequestState.Success->{
                        setState { copy(isLoading = false, posts = state.data) }
                    }
                    is RequestState.Error->{
                        setState { copy(isLoading = false) }
                        emitEffect { SocietyHomeEffect.ShowToast(state.message) }
                    }
                }
            }
        }
    }

    private fun getCategories() {
        launchScope {
            getPostsCategoriesUseCase(Unit).collect { state->
                when(state){
                    is RequestState.Loading->{
                        setState { copy(isLoading = true) }
                    }
                    is RequestState.Success->{
                        setState { copy(isLoading = false, categories = state.data) }
                    }
                    is RequestState.Error->{
                        setState { copy(isLoading = false) }
                        emitEffect { SocietyHomeEffect.ShowToast(state.message) }
                    }
                }
            }
        }
    }

    override suspend fun handleIntent(intent: SocietyHomeIntent) {
        val currentState = getCurrentState()
        when(intent){
            is SocietyHomeIntent.ChangeCategory->{
                if (currentState.selectedCategory!=intent.index){
                    setState { copy(selectedCategory = intent.index) }
                    getPosts(currentState.categories[intent.index].id)
                }else{
                    setState {
                        copy(
                            selectedCategory = -1
                        )
                    }
                    getPosts(null)
                }
            }
            is SocietyHomeIntent.ChangePostLike->{
                changePostStatus(intent.id,currentState.posts)
            }
            is SocietyHomeIntent.OnPostClicked->{
                if (intent.id!=null){
                    emitEffect { SocietyHomeEffect.Nav(AppRoutes.PostDetails(intent.id)) }
                }
            }
        }
    }

    private fun changePostStatus(id: Int?, posts: List<PostDto>) {
        if (id!=null){
            setState {
                copy(
                    posts = posts.map {
                        if (it.id == id){
                            val currentLikes = it.likesCount ?: 0
                            val isCurrentlyLiked = it.likeStatus == true
                            it.copy(likeStatus = !isCurrentlyLiked,
                                likesCount = if (isCurrentlyLiked) currentLikes - 1 else currentLikes + 1
                            )
                        }else{
                            it
                        }
                    }
                )
            }
            launchScope {
                changePostLikeStatusUseCase(id).collect { state->
                    when(state){
                        is RequestState.Error->{
                            setState {
                                copy(
                                    posts = posts.map {
                                        if (it.id == id){
                                            val currentLikes = it.likesCount ?: 0
                                            val isCurrentlyLiked = it.likeStatus == true
                                            it.copy(likeStatus = !isCurrentlyLiked,
                                                likesCount = if (isCurrentlyLiked) currentLikes - 1 else currentLikes + 1)
                                        }else{
                                            it
                                        }
                                    }
                                )
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}