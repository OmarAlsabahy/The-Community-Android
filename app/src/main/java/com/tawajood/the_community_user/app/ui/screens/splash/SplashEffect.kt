package com.tawajood.the_community_user.app.ui.screens.splash

import com.tawajood.the_community_user.app.base.UiEffect

sealed interface SplashEffect : UiEffect {
    data object NavToOnBoarding : SplashEffect
    data object NavToAuth : SplashEffect
    data object NavToHome : SplashEffect
}