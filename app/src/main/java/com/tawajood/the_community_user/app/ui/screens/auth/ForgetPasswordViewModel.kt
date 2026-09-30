package com.tawajood.the_community_user.app.ui.screens.auth

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.usecase.auth.ForgetPasswordUseCase
import com.tawajood.the_community_user.utils.isPhoneValidForCountry
import com.vanniktech.locale.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class ForgetPasswordUiState(
    val isLoading: Boolean = false,
    val phoneFieldValue: String = "",
    val selectedCountry : Country = Country.EGYPT,
    val isButtonEnabled : Boolean = false
): UiState
sealed interface ForgetPasswordIntent: UiIntent{
    data class OnPhoneChanges(val value: String): ForgetPasswordIntent
    data object ForgetPassword: ForgetPasswordIntent
}
sealed interface ForgetPasswordEffect: UiEffect{
    data class ShowErrorToast(val message: String): ForgetPasswordEffect
    data class Nav(val route: AppRoutes): ForgetPasswordEffect
}
@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val forgetPasswordUseCase: ForgetPasswordUseCase
)
    : BaseViewModel<ForgetPasswordUiState,ForgetPasswordIntent,ForgetPasswordEffect>(ForgetPasswordUiState()) {
    override suspend fun handleIntent(intent: ForgetPasswordIntent) {
        val currentState = getCurrentState()
        when(intent){
            is ForgetPasswordIntent.OnPhoneChanges->{
               setState {
                   copy(
                       phoneFieldValue = intent.value,
                       isButtonEnabled = isPhoneValidForCountry(intent.value.trim(),currentState.selectedCountry)
                   )
               }
            }
            is ForgetPasswordIntent.ForgetPassword -> {
                forgetPassword(currentState.phoneFieldValue,currentState.selectedCountry)
            }
        }
    }

    private fun forgetPassword(phoneFieldValue: String, selectedCountry: Country) {
        launchScope {
            forgetPasswordUseCase(phoneFieldValue).collect { state->
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
                                isLoading = false
                            )
                        }
                        emitEffect {
                            ForgetPasswordEffect.Nav(AppRoutes.VerifyOtpScreen(selectedCountry.callingCodes.first(),phoneFieldValue.trim()))
                        }
                    }
                    is RequestState.Error->{
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                        emitEffect{
                            ForgetPasswordEffect.ShowErrorToast(state.message)
                        }
                    }
                }
            }
        }
    }
}