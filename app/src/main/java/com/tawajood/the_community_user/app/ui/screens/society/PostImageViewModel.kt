package com.tawajood.the_community_user.app.ui.screens.society

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.usecase.society.ChangePostLikeStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
data class PostImageUiState(
    val post: PostDto? = null,
    val isPageLoaded: Boolean = false
): UiState
sealed interface PostImageIntent: UiIntent{
    data class loadPage(val post: PostDto): PostImageIntent
    data object ChangeLikeStatus: PostImageIntent
    data object OnCommentClicked: PostImageIntent
}
sealed interface PostImageEffect: UiEffect{
    data class ShowToast(val message: String): PostImageEffect
    data class Nav(val route: AppRoutes): PostImageEffect
}
@HiltViewModel
class PostImageViewModel @Inject constructor(
    private val changePostLikeStatusUseCase: ChangePostLikeStatusUseCase,
): BaseViewModel<PostImageUiState,PostImageIntent,PostImageEffect>(PostImageUiState()) {
    override suspend fun handleIntent(intent: PostImageIntent) {
        val currentState = getCurrentState()
        when(intent){
            is PostImageIntent.loadPage->{
                setState {
                    copy(
                        post = intent.post,
                        isPageLoaded = true
                    )
                }
            }
            is PostImageIntent.ChangeLikeStatus->{
                if (currentState.post?.id!=null){
                    setState {
                        copy(
                            post = post?.copy(likeStatus = if (post.likeStatus == false) true else false,
                                likesCount = if (post.likeStatus == false) (post.likesCount?:0)+1 else (post.likesCount?:0)-1)
                        )
                    }
                    changeLikeStatus(currentState.post.id)
                }

            }
            is PostImageIntent.OnCommentClicked->{
                if (currentState.post?.id!=null){
                    emitEffect{PostImageEffect.Nav(AppRoutes.PostDetails(currentState.post.id))}
                }
            }
        }
    }

    private fun changeLikeStatus(id: Int) {
        launchScope {
            changePostLikeStatusUseCase(id).collect { state->
                when(state){
                    is RequestState.Error->{
                        setState {
                            copy(
                                post = post?.copy(likeStatus = if (post.likeStatus == false) true else false,
                                    likesCount = if (post.likeStatus == false) (post.likesCount?:0)+1 else (post.likesCount?:0)-1)
                            )
                        }
                        emitEffect{PostImageEffect.ShowToast(state.message)}
                    }
                    else -> {}
                }
            }
        }
    }
}