package com.tawajood.the_community_user.app.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Splash

@Serializable
data object OnBoarding

@Serializable
data object Login

@Serializable
data object ForgetPassword

enum class OtpType {
    LOGIN,
    REGISTER
}

@Serializable
data class Otp(
    val countryCode: String,
    val phoneNumber: String,
    val otpType: OtpType
)

@Serializable
data class ResetPassword(
    val countryCode: String,
    val phoneNumber: String,
    val otp: String
)

@Serializable
data object Signup

@Serializable
data object TermsAndConditions
