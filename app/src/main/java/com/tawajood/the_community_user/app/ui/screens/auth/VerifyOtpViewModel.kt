package com.tawajood.the_community_user.app.ui.screens.auth

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.domain.base.RequestState
import com.tawajood.the_community_user.domain.usecase.auth.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

data class VerifyOtpUiState(
    val isLoading: Boolean = false,
    val otpValue: String = "",
    val isButtonEnabled : Boolean = false,
    val timer : Int = 0
): UiState
sealed interface VerifyOtpIntent: UiIntent{
    data class OnOtpChanges(val value: String): VerifyOtpIntent
    data class OnSubmitPressed(val phone: String): VerifyOtpIntent
    data object RecreateTimer: VerifyOtpIntent
}
sealed interface VerifyOtpEffect: UiEffect{
    data class Nav(val route: AppRoutes): VerifyOtpEffect
    data class ShowToast(val message: String):VerifyOtpEffect
}
@HiltViewModel
class VerifyOtpViewModel @Inject constructor(
    private val verifyOtpUseCase: VerifyOtpUseCase
): BaseViewModel<VerifyOtpUiState, VerifyOtpIntent, VerifyOtpEffect>(VerifyOtpUiState()) {
    init {
        loadPage()
    }

    private fun loadPage() {
        startTimer()
    }

    private fun startTimer() {
        launchScope {
            for (counter in 60 downTo 0){
                setState {
                    copy(
                        timer = counter
                    )
                }
                delay(1000.milliseconds)
            }
        }
    }

    override suspend fun handleIntent(intent: VerifyOtpIntent) {
        val currentState = getCurrentState()
        when(intent){
            is VerifyOtpIntent.OnOtpChanges->{
                setState {
                    copy(
                        otpValue = intent.value,
                        isButtonEnabled = intent.value.length == 5
                    )
                }
            }
            is VerifyOtpIntent.OnSubmitPressed->{
                verifyOtp(intent.phone,currentState.otpValue)
            }
            is VerifyOtpIntent.RecreateTimer->{
                startTimer()
            }
        }
    }

    private fun verifyOtp(phone: String, otpValue: String) {
        launchScope {
            verifyOtpUseCase(
                VerifyOtpUseCase.VerifyOtpRequest(phone,otpValue)
            ).collect { state->
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
                        if (state.data.resetToken!=null){
                            emitEffect { VerifyOtpEffect.Nav(AppRoutes.NewPassword(state.data.resetToken,phone.trim())) }
                        }
                    }
                    is RequestState.Error->{
                        setState {
                            copy(
                                isLoading = false
                            )
                        }
                        emitEffect { VerifyOtpEffect.ShowToast(state.message) }
                    }
                }
            }
        }
    }
}