package com.tawajood.the_community_user.app.language

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun LocalizationProvider(
    lang: AppLanguage,
    content: @Composable () -> Unit
) {
    val strings: Strings = remember(lang) {
        when (lang) {
            AppLanguage.ARABIC -> StringsAr()
            AppLanguage.ENGLISH -> StringsEn()
        }
    }

    val dir = if (lang == AppLanguage.ARABIC) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(
        LocalAppLocale provides AppLocale(lang),
        LocalStrings provides strings,
        LocalLayout provides dir
    ) {
        content()
    }
}