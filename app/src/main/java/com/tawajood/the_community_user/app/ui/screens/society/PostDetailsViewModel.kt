package com.tawajood.the_community_user.app.ui.screens.society

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel
import com.tawajood.the_community_user.domain.models.society.Comment
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.models.society.User
import com.tawajood.the_community_user.domain.usecase.profile.GetProfileUseCase
import com.tawajood.the_community_user.domain.usecase.society.AddCommentUseCase
import com.tawajood.the_community_user.domain.usecase.society.ChangeCommentLikeStatusUseCase
import com.tawajood.the_community_user.domain.usecase.society.ChangePostLikeStatusUseCase
import com.tawajood.the_community_user.domain.usecase.society.GetPostDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class PostDetailsUiState(
    val isLoading: Boolean = false,
    val post: PostDto? = null,
    val isPageLoaded: Boolean = false,
    val profile: ProfileResponseModel? = null,
    val commentFieldValue: String = "",

): UiState
sealed interface PostDetailsIntent: UiIntent {
    data class LoadPage(val id: Int):PostDetailsIntent
    data object OnLikeClicked:PostDetailsIntent
    data class OnCommentFieldChanges(val value: String): PostDetailsIntent
    data object SendComment: PostDetailsIntent
    data class ChangeCommentStatus(val id: Int?): PostDetailsIntent
}
sealed interface PostDetailsEffect: UiEffect {
    data class ShowToast(val message: String): PostDetailsEffect
    data class RevertData(val post: PostDto?): PostDetailsEffect
}
@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val getPostDetails: GetPostDetailsUseCase,
    private val changePostLikeStatusUseCase: ChangePostLikeStatusUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val changeCommentLikeStatusUseCase: ChangeCommentLikeStatusUseCase
): BaseViewModel<PostDetailsUiState,PostDetailsIntent,PostDetailsEffect>(PostDetailsUiState()) {
    init {
        getProfile()
    }

    private fun getProfile() {
        launchScope {
            getProfileUseCase(Unit).collect { state ->
                when (state) {
                    is RequestState.Success -> {
                        setState {
                            copy(
                                profile = state.data
                            )
                        }
                    }

                    is RequestState.Error -> {
                        emitEffect { PostDetailsEffect.ShowToast(state.message) }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun changeCommentStatus(id: Int) {
        launchScope {
            changeCommentLikeStatusUseCase(id).collect { state->
                when(state){
                    is RequestState.Error->{
                        setState {
                            copy(
                                post = post?.copy(
                                    comments = post.comments?.map {
                                        if (it?.id == id){
                                            it.copy(likeStatus = !(it.likeStatus?:false))
                                        }else{
                                            it
                                        }
                                    }
                                )
                            )
                        }
                        emitEffect { PostDetailsEffect.ShowToast(state.message) }
                    }
                    else -> {}
                }
            }
        }
    }

    override suspend fun handleIntent(intent: PostDetailsIntent) {
        val currentState = getCurrentState()
        when(intent){
            is PostDetailsIntent.LoadPage->{
                loadPage(intent.id)
            }
            is PostDetailsIntent.OnLikeClicked->{
                changeLikeStatus(currentState.post?.id, revertData = {
                    emitEffect { PostDetailsEffect.RevertData(currentState.post) }
                })
            }
            is PostDetailsIntent.OnCommentFieldChanges->{
                setState { copy(commentFieldValue = intent.value) }
            }
            is PostDetailsIntent.SendComment->{
                sendComment(currentState.post?.id,currentState.commentFieldValue,currentState.profile,
                    revertData = {
                        emitEffect { PostDetailsEffect.RevertData(currentState.post) }
                    })
            }
            is PostDetailsIntent.ChangeCommentStatus->{
                if (intent.id!=null){
                    setState {
                        copy(
                            post = post?.copy(
                                comments = post.comments?.map {
                                    if (it?.id == intent.id){
                                        it.copy(likeStatus = if (it.likeStatus==true) false else true)
                                    }else{
                                        it
                                    }
                                }
                            )
                        )
                    }
                    changeCommentStatus(intent.id)
                }
            }
        }
    }

    private fun sendComment(id: Int?, commentFieldValue: String, profile: ProfileResponseModel?,
                            revertData:()-> Unit) {
        if (id != null && commentFieldValue.isNotEmpty()) {
            val newComment = Comment(
                id = null,
                comment = commentFieldValue,
                user = User(
                    id = null,
                    name = profile?.name,
                    image = profile?.image
                ),
                replies = null,
                likeStatus = null,
            )
            setState {
                copy(
                    commentFieldValue = "",
                    post = post?.copy(
                        commentsCount = (post.commentsCount ?: 0) + 1,
                        comments = (post.comments ?: emptyList()) + newComment
                    )
                )
            }
            launchScope {
                addCommentUseCase(AddCommentUseCase.AddCommentRequest(id, commentFieldValue)).collect { state ->
                    when (state) {
                        is RequestState.Error -> {
                            setState {
                                copy(
                                    post = post?.copy(
                                        commentsCount = maxOf(0, (post.commentsCount ?: 1) - 1),
                                        comments = post.comments?.filter { it?.comment != commentFieldValue }
                                    )
                                )
                            }
                            revertData()
                            emitEffect {
                                PostDetailsEffect.ShowToast(state.message)
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun changeLikeStatus(id: Int?,revertData: () -> Unit) {
        if (id!=null){
            setState {
                copy(
                    post = post?.copy(
                        likeStatus = !(post.likeStatus?:false),
                        likesCount = if (post.likeStatus==true) post.likesCount?.minus(1) else post.likesCount?.plus(1)
                    )
                )
            }
            launchScope {
                changePostLikeStatusUseCase(id).collect { state->
                    when(state){
                        is RequestState.Error->{
                            setState {
                                copy(
                                    post = post?.copy(
                                        likeStatus = !(post.likeStatus?:false),
                                        likesCount = if (post.likeStatus==true) post.likesCount?.plus(1) else post.likesCount?.minus(1)
                                    )
                                )
                            }
                            revertData()
                            emitEffect { PostDetailsEffect.ShowToast(state.message) }
                        }
                        else -> {}
                    }
                }
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