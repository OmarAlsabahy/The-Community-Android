package com.tawajood.the_community_user.app.ui.screens.customerServices

import android.content.Context
import android.net.Uri
import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.customerServices.AddComplaintRequest
import com.tawajood.the_community_user.domain.usecase.customerServices.AddComplaintUseCase
import com.tawajood.the_community_user.utils.FileUtils
import com.tawajood.the_community_user.utils.FileUtils.Companion.convertFileToMultiPart
import com.tawajood.the_community_user.utils.compressImageToSize
import com.vanniktech.locale.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

data class AddComplaintUiState(
    val isLoading: Boolean = false,
    val phoneFieldValue: String = "",
    val addressFieldValue: String="",
    val complaintFieldValue: String = "",
    val selectedCountry: Country = Country.SAUDI_ARABIA,
    val selectedImages : List<Uri> = emptyList(),
    val showSuccessDialog: Boolean = false
): UiState
sealed interface AddComplaintIntent: UiIntent{
    data class OnPhoneValueChanges(val value: String): AddComplaintIntent
    data class OnAddressValueChanges(val value: String): AddComplaintIntent
    data class OnComplaintValueChanges(val value: String): AddComplaintIntent
    data object OnSelectMediaPressed: AddComplaintIntent
    data class AddMedia(val mediaUris: List<Uri>): AddComplaintIntent
    data class OnSubmit(val categoryId: Int): AddComplaintIntent
    data object ReturnToHome: AddComplaintIntent
}
sealed interface AddComplaintEffect: UiEffect{
    data object SelectMedia: AddComplaintEffect
    data class ShowToast(val message: String): AddComplaintEffect
    data class ReturnToHome(val route: AppRoutes): AddComplaintEffect
}
@HiltViewModel
class AddComplaintViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val addComplaintUseCase: AddComplaintUseCase,
): BaseViewModel<AddComplaintUiState
        ,AddComplaintIntent,AddComplaintEffect>(AddComplaintUiState()) {
    override suspend fun handleIntent(intent: AddComplaintIntent) {
        val currentState = getCurrentState()
        when(intent){
            is AddComplaintIntent.OnPhoneValueChanges->{
                setState { copy(phoneFieldValue = intent.value) }
            }
            is AddComplaintIntent.OnAddressValueChanges->{
                setState { copy(addressFieldValue = intent.value) }
            }
            is AddComplaintIntent.OnComplaintValueChanges->{
                setState { copy(complaintFieldValue = intent.value) }
            }
            is AddComplaintIntent.OnSelectMediaPressed->{
                emitEffect { AddComplaintEffect.SelectMedia }
            }
            is AddComplaintIntent.AddMedia->{
                setState {
                    copy(
                        selectedImages = intent.mediaUris
                    )
                }
            }
            is AddComplaintIntent.OnSubmit->{
                validateData(intent.categoryId,currentState)
            }
            is AddComplaintIntent.ReturnToHome->{
                emitEffect { AddComplaintEffect.ReturnToHome(AppRoutes.Main) }
            }
        }
    }
    private fun validateData(categoryId: Int, currentState: AddComplaintUiState) {
        if (currentState.phoneFieldValue.isNotEmpty() && currentState.addressFieldValue.isNotEmpty()
            && currentState.complaintFieldValue.isNotEmpty()){
            val media = mutableListOf<MultipartBody.Part>()
            currentState.selectedImages.forEach {
                val mediaFile = FileUtils.convertUriToFile(context,it)
                if (mediaFile!=null){
                    val compressedFile = compressImageToSize(mediaFile)
                    val multiPartFile = convertFileToMultiPart("media[]",compressedFile)
                    media.add(multiPartFile)
                }
            }
            val request = AddComplaintRequest(
                categoryId = categoryId.toString().toRequestBody(),
                phone = currentState.phoneFieldValue.trim().toRequestBody(),
                address = currentState.addressFieldValue.trim().toRequestBody(),
                complaint = currentState.complaintFieldValue.trim().toRequestBody(),
                countryCode = currentState.selectedCountry.callingCodes.first().toRequestBody(),
                description = currentState.complaintFieldValue.trim().toRequestBody(),
                media = media.ifEmpty { null }
            )
            launchScope {
                addComplaintUseCase(request).collect { state->
                    when(state){
                        is RequestState.Loading ->{
                            setState {
                                copy(isLoading = true)
                            }
                        }
                        is RequestState.Success->{
                            setState {
                                copy(
                                    isLoading = false,
                                    showSuccessDialog = true
                                )
                            }
                        }
                        is RequestState.Error->{
                            setState {
                                copy(isLoading = false)
                            }
                            emitEffect { AddComplaintEffect.ShowToast(state.message) }
                        }
                    }
                }
            }
        }
    }

}