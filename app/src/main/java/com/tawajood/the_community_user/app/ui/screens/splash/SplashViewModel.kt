package com.tawajood.the_community_user.app.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.tawajood.the_community_user.app.base.BaseViewModel
import com.tawajood.the_community_user.domain.usecase.auth.CheckTokenUseCase
import com.tawajood.the_community_user.domain.usecase.on_boarding.IsOnBoardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isOnBoardingShownUseCase: IsOnBoardingShownUseCase,
//    private val isUserLoginUseCase: IsUserLoginUseCase
    private val checkUserTokenUseCase: CheckTokenUseCase
) : BaseViewModel<SplashState, SplashIntent, SplashEffect>(SplashState()) {

    init {
        finishSplash()
    }

    override suspend fun handleIntent(intent: SplashIntent) {
        when (intent) {
            else -> {}
        }
    }

    private fun finishSplash() {
        viewModelScope.launch(Dispatchers.IO) {
            val isOnBoardingDeferred = async { isOnBoardingShownUseCase() }
//            val isUserLoginDeferred = async { isUserLoginUseCase() }
            val isGifFinishedDeferred = async {
                delay(2500)
                true
            }

            val isOnBoardingShown = isOnBoardingDeferred.await()
            //val isUserLogin = isUserLoginDeferred.await()
            val isGifFinished = isGifFinishedDeferred.await()
            val result = viewModelScope.async(Dispatchers.IO){
                checkUserTokenUseCase.checkToken()
            }
            if (result.await()){
                emitEffect { SplashEffect.NavToHome }
            }else{
                emitEffect { SplashEffect.NavToAuth }
            }

            if (false && isGifFinished) {
//                emitEffect { SplashEffect.NavToHome }
            } else {
//                emitEffect { SplashEffect.NavToAuth }
            }
        }
    }
}