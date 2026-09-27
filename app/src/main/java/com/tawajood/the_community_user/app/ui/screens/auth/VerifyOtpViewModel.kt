package com.tawajood.the_community_user.app.ui.screens.auth

import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.app.base.UiEffect
import com.tawajood.the_community_user.app.base.UiIntent
import com.tawajood.the_community_user.app.base.UiState
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
    data object OnSubmitPressed: VerifyOtpIntent
    data object RecreateTimer: VerifyOtpIntent
}
sealed interface VerifyOtpEffect: UiEffect
@HiltViewModel
class VerifyOtpViewModel @Inject constructor(): BaseViewModel<VerifyOtpUiState, VerifyOtpIntent, VerifyOtpEffect>(VerifyOtpUiState()) {
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
            }
            is VerifyOtpIntent.RecreateTimer->{
                startTimer()
            }
        }
    }
}