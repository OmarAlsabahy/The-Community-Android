package com.tawajood.the_community_user.app.ui.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController

private const val ERROR_MESSAGE = "No navigation host controller provided."

val LocalNavigationProvider = staticCompositionLocalOf<NavHostController> {
    error(ERROR_MESSAGE)
}
