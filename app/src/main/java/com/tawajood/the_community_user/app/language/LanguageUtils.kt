package com.tawajood.the_community_user.app.language

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.LayoutDirection

@Stable
data class AppLocale(val lang: AppLanguage)

val LocalAppLocale = staticCompositionLocalOf { AppLocale(AppLanguage.ARABIC) }

val LocalStrings = staticCompositionLocalOf<Strings> {
    error("LocalStrings not provided")
}

val LocalLayout = staticCompositionLocalOf { LayoutDirection.Rtl }
