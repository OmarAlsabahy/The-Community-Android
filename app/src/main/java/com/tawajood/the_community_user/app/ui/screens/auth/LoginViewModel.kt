package com.tawajood.the_community_user.app.ui.screens.auth

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.models.auth.LoginRequest
import com.tawajood.the_community_user.domain.usecase.auth.LoginUseCase
import com.tawajood.the_community_user.domain.usecase.auth.SaveTokenUseCase
import com.tawajood.the_community_user.utils.isPhoneValidForCountry
import com.vanniktech.locale.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
data class LoginUiState(
    val isLoading: Boolean = false,
    val phoneValue: String = "",
    val passwordValue: String = "",
    val isPasswordSecured: Boolean = true,
    val selectedCountry : Country = Country.SAUDI_ARABIA,
    val showCountryDialog: Boolean = false,
    val isSubmitButtonEnabled: Boolean = false
): UiState
sealed interface LoginIntent : UiIntent{
    data class OnPhoneChanges(val value: String): LoginIntent
    data class OnPasswordChanges(val value: String): LoginIntent
    data object OnSubmitPressed : LoginIntent
    data object OnUserCyclePressed : LoginIntent
    data object OnCountryPressed : LoginIntent
    data object OnCountryDialogDismissed : LoginIntent
    data class OnCountrySelected(val country: Country) : LoginIntent
    data object OnChangePasswordVisibility: LoginIntent
}
sealed interface LoginEffect : UiEffect {
    data class ShowErrorToast(val message: String): LoginEffect
    data class Nav(val route: AppRoutes): LoginEffect
}
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
)
    : BaseViewModel<LoginUiState , LoginIntent , LoginEffect>(LoginUiState()) {
    override suspend fun handleIntent(intent: LoginIntent) {
        val currentState = getCurrentState()
        when(intent){
            is LoginIntent.OnPhoneChanges -> {
                setState {
                    copy(
                        phoneValue = intent.value,
                        isSubmitButtonEnabled = (isPhoneValidForCountry(intent.value.trim(),currentState.selectedCountry)
                                && currentState.passwordValue.trim().isNotEmpty())
                    )
                }
            }
            is LoginIntent.OnPasswordChanges -> {
                setState {
                    copy(passwordValue = intent.value, isSubmitButtonEnabled = (isPhoneValidForCountry(currentState.phoneValue.trim(),currentState.selectedCountry)
                            && intent.value.trim().isNotEmpty()))
                }
            }
            is LoginIntent.OnSubmitPressed->{
                login(currentState.phoneValue.trim() , currentState.passwordValue.trim())
            }
            is LoginIntent.OnUserCyclePressed->{

            }
            is LoginIntent.OnCountryPressed->{
                setState {
                    copy(
                        showCountryDialog = true
                    )
                }
            }
            is LoginIntent.OnCountryDialogDismissed->{
                setState {
                    copy(
                        showCountryDialog = false
                    )
                }
            }
            is LoginIntent.OnCountrySelected->{
                setState {
                    copy(
                        selectedCountry = intent.country
                    )
                }
            }
            is LoginIntent.OnChangePasswordVisibility->{
                setState {
                    copy(
                        isPasswordSecured = !isPasswordSecured
                    )
                }
            }
        }
    }

    private fun login(phone: String, password: String) {
        val request = LoginRequest(
            password =password,
            phone = phone
        )
        launchScope {
            loginUseCase(request).collect { state->
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
                        if (state.data!=null){
                            saveTokenUseCase.saveToken(state.data)
                        }
                        emitEffect { LoginEffect.Nav(AppRoutes.Home) }
                    }
                    is RequestState.Error->{
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                        emitEffect {
                            LoginEffect.ShowErrorToast(state.message)
                        }
                    }
                }
            }
        }
    }
}