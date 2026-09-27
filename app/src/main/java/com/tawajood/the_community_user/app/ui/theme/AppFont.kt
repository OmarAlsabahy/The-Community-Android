package com.tawajood.the_community_user.app.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.tawajood.the_community_user.R

object AppFont {
    val AlmaraiFont = FontFamily(
        Font(R.font.almarai_regular),
        Font(R.font.almarai_light, FontWeight.Light),
        Font(R.font.almarai_regular, FontWeight.Medium),
        Font(R.font.almarai_bold, FontWeight.Bold),
    )
}