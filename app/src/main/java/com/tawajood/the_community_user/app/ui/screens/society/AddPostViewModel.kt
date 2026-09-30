package com.tawajood.the_community_user.app.ui.screens.society

import android.net.Uri
import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.usecase.profile.GetProfileUseCase
import com.tawajood.the_community_user.domain.usecase.society.CreatePostUseCase
import com.tawajood.the_community_user.domain.usecase.society.GetPostsCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AddPostUiState(
    val isLoading: Boolean = false,
    val postsCategories: List<PostCategoriesDto> = emptyList(),
    val selectedCategory : PostCategoriesDto?=null,
    val profile: ProfileResponseModel? = null,
    val postFieldValue: String = "",
    val mediaImages : List<Uri> = emptyList(),
    val isButtonEnabled: Boolean = false,
): UiState
sealed interface AddPostIntent: UiIntent{
    data class OnCategoryClicked(val category: PostCategoriesDto): AddPostIntent
    data class OnPostValueChanges(val value: String): AddPostIntent
    data object OnImagerPickerClicked: AddPostIntent
    data class AddImages(val uris: List<Uri>): AddPostIntent
    data class OnDeleteImage(val uri: Uri): AddPostIntent
    data object OnSubmitPressed: AddPostIntent
}
sealed interface AddPostEffect: UiEffect{
    data class ShowToast(val message: String): AddPostEffect
    data object PickImages: AddPostEffect
    data class Pop(val post: PostDto): AddPostEffect
}
@HiltViewModel
class AddPostViewModel @Inject constructor(
    private val getPostsCategoriesUseCase: GetPostsCategoriesUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val createPostUseCase: CreatePostUseCase
): BaseViewModel<AddPostUiState, AddPostIntent, AddPostEffect>(AddPostUiState()) {
    init {
        loadPage()
    }

    private fun loadPage() {
        getProfile()
        getPostsCategories()
    }

    private fun getProfile() {
        launchScope {
            getProfileUseCase(Unit).collect { state->
                when(state){
                    is RequestState.Success->{
                        setState {
                            copy(
                                profile = state.data
                            )
                        }
                    }
                    is RequestState.Error -> {
                        emitEffect { AddPostEffect.ShowToast(state.message) }
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getPostsCategories() {
        launchScope {
            getPostsCategoriesUseCase(Unit).collect { state->
                when(state){
                    is RequestState.Loading->{
                        setState {
                            copy(
                                isLoading = true,
                                postsCategories = emptyList()
                            )
                        }
                    }
                    is RequestState.Success->{
                        setState {
                            copy(
                                isLoading = false,
                                postsCategories = state.data
                            )
                        }
                    }
                    is RequestState.Error->{
                        setState {
                            copy(
                                isLoading = false,
                                postsCategories = emptyList()
                            )
                        }
                        emitEffect { AddPostEffect.ShowToast(state.message) }
                    }
                }
            }
        }
    }

    override suspend fun handleIntent(intent: AddPostIntent) {
        val currentState = getCurrentState()
        when(intent){
            is AddPostIntent.OnCategoryClicked->{
                if (currentState.selectedCategory != intent.category){
                    setState {
                        copy(
                            selectedCategory = intent.category,
                            isButtonEnabled = postFieldValue.isNotEmpty()
                        )
                    }
                }else{
                    setState {
                        copy(
                            selectedCategory = null,
                            isButtonEnabled = false
                        )
                    }
                }
            }
            is AddPostIntent.OnPostValueChanges->{
                setState {
                    copy(
                        postFieldValue = intent.value,
                        isButtonEnabled = intent.value.isNotEmpty() && selectedCategory!=null
                    )
                }
            }
            is AddPostIntent.OnImagerPickerClicked->{
                emitEffect { AddPostEffect.PickImages }
            }
            is AddPostIntent.AddImages->{
                setState {
                    copy(
                        mediaImages = intent.uris,
                    )
                }
            }
            is AddPostIntent.OnDeleteImage->{
                setState {
                    copy(
                        mediaImages = mediaImages.filter { it!=intent.uri }
                    )
                }
            }
            is AddPostIntent.OnSubmitPressed->{
                if (currentState.selectedCategory?.id!=null){
                    createPost()
                }
            }
        }
    }

    private fun createPost() {
        launchScope {
            createPostUseCase(CreatePostUseCase.CreatePostRequest(
                content = getCurrentState().postFieldValue,
                categoryId = getCurrentState().selectedCategory?.id!!,
                media = getCurrentState().mediaImages
            )).collect { state->
                when(state) {
                    is RequestState.Loading -> {
                        setState {
                            copy(
                                isLoading = true
                            )
                        }
                    }
                    is RequestState.Success -> {
                        setState {
                            copy(
                                isLoading = false,
                                postFieldValue = "",
                                selectedCategory = null,
                                mediaImages = emptyList(),
                            )
                        }
                        emitEffect { AddPostEffect.Pop(state.data) }
                    }
                    is RequestState.Error -> {
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                        emitEffect { AddPostEffect.ShowToast(state.message) }
                    }
                }
            }
        }
    }
}