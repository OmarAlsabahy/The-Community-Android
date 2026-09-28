package com.tawajood.the_community_user.app.ui.screens.society

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.usecase.society.GetPostDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class PostDetailsUiState(
    val isLoading: Boolean = false,
    val post: PostDto? = null,
    val isPageLoaded: Boolean = false
): UiState
sealed interface PostDetailsIntent: UiIntent {
    data class LoadPage(val id: Int):PostDetailsIntent
}
sealed interface PostDetailsEffect: UiEffect {
    data class ShowToast(val message: String): PostDetailsEffect
}
@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val getPostDetails: GetPostDetailsUseCase
): BaseViewModel<PostDetailsUiState,PostDetailsIntent,PostDetailsEffect>(PostDetailsUiState()) {
    override suspend fun handleIntent(intent: PostDetailsIntent) {
        when(intent){
            is PostDetailsIntent.LoadPage->{
                loadPage(intent.id)
            }
        }
    }

    private fun loadPage(id: Int) {
        launchScope {
            getPostDetails(id).collect { state->
                when(state){
                    is RequestState.Loading->{
                        setState { copy(isLoading = true) }
                    }
                    is RequestState.Success->{
                        setState { copy(isLoading = false, post = state.data, isPageLoaded = true) }
                    }
                    is RequestState.Error->{
                        setState { copy(isLoading = false) }
                        emitEffect { PostDetailsEffect.ShowToast(state.message) }
                    }
                }
            }
        }
    }
}