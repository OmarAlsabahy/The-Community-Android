package com.tawajood.the_community_user.app.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoutes {
    @Serializable
    data object Splash : AppRoutes

    @Serializable
    data object Login: AppRoutes
    @Serializable
    data class VerifyOtpScreen(val countryCode: String,val phoneNumber: String): AppRoutes

    @Serializable
    data object Home: AppRoutes


}