package com.tawajood.the_community_user.app.ui.navigation

import kotlinx.serialization.Serializable

enum class AuthStartDestination {
    Splash, Login
}

@Serializable
data class Auth(
    val startDestination: AuthStartDestination
)

@Serializable
data object VehicleInfo

@Serializable
data object CompanyInfo

@Serializable
data object JoiningContract

@Serializable
data object App