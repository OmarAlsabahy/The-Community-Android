package com.tawajood.the_community_user.app.ui.screens.auth

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.usecase.auth.ResetPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class NewPasswordUiState(
    val isLoading: Boolean = false,
    val passwordFieldValue: String ="",
    val confirmPasswordFieldValue : String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible : Boolean = false,
    val isButtonEnabled: Boolean = false
): UiState
sealed interface NewPasswordIntent: UiIntent{
    data class OnPasswordChanges(val value: String): NewPasswordIntent
    data class OnConfirmPasswordChanges(val value: String): NewPasswordIntent
    data object ChangePasswordVisibility: NewPasswordIntent
    data object ChangeConfirmPasswordVisibility: NewPasswordIntent
    data class OnSubmitPressed(val phone: String,val resetToken: String): NewPasswordIntent
}
sealed interface NewPasswordEffect: UiEffect{
    data class ShowToast(val message: String): NewPasswordEffect
    data class Nav(val route: AppRoutes): NewPasswordEffect
}
@HiltViewModel
class NewPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
): BaseViewModel<NewPasswordUiState,NewPasswordIntent,NewPasswordEffect>(NewPasswordUiState()) {
    override suspend fun handleIntent(intent: NewPasswordIntent) {
        val currentState = getCurrentState()
        when(intent){
            is NewPasswordIntent.OnPasswordChanges->{
                setState {
                    copy(
                        passwordFieldValue = intent.value,
                        isButtonEnabled = (intent.value.trim().isNotEmpty()) && (intent.value.trim() == currentState.confirmPasswordFieldValue.trim())
                    )
                }
            }
            is NewPasswordIntent.OnConfirmPasswordChanges->{
                setState {
                    copy(
                        confirmPasswordFieldValue = intent.value,
                        isButtonEnabled = (intent.value.trim().isNotEmpty()) && (intent.value.trim() == currentState.passwordFieldValue.trim())
                    )
                }
            }
            is NewPasswordIntent.ChangePasswordVisibility->{
                setState {
                    copy(
                        isPasswordVisible = !isPasswordVisible
                    )
                }
            }
            is NewPasswordIntent.ChangeConfirmPasswordVisibility->{
                setState {
                    copy(
                        isConfirmPasswordVisible = !isConfirmPasswordVisible
                    )
                }
            }
            is NewPasswordIntent.OnSubmitPressed->{
                resetPassword(currentState.passwordFieldValue.trim(),currentState.confirmPasswordFieldValue.trim(),
                    intent.phone,intent.resetToken)
            }
        }
    }

    private fun resetPassword(
        passwordFieldValue: String,
        confirmPasswordFieldValue: String,
        phone: String,
        resetToken: String
    ) {
        launchScope {
            resetPasswordUseCase(
                ResetPasswordUseCase.ResetPasswordRequest(
                    resetToken = resetToken,
                    password = passwordFieldValue,
                    confirmPassword = confirmPasswordFieldValue,
                    phone = phone
                )
            ).collect { state ->
                when (state) {
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
                                isLoading = false
                            )
                        }
                        emitEffect { NewPasswordEffect.Nav(AppRoutes.Login) }
                    }
                    is RequestState.Error -> {
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                        emitEffect { NewPasswordEffect.ShowToast(state.message) }
                    }
                }

            }
        }
    }
}