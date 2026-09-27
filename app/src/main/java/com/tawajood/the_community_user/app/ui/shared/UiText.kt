package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.app.ui.theme.AppFont

@Composable
fun UiText(
    text: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    color: Color = Color.Black,
    fontWeight: FontWeight? = FontWeight.W400,
    textAlign: TextAlign? = null,
    fontFamily: FontFamily = AppFont.AlmaraiFont,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    textDecoration: TextDecoration? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
) {
    Text(
        text = text,
        fontSize = fontSize,
        color = color,
        modifier = modifier,
        fontWeight = fontWeight,
        textAlign = textAlign,
        fontFamily = fontFamily,
        minLines = minLines,
        maxLines = maxLines,
        overflow = overflow,
        textDecoration = textDecoration,
        lineHeight = lineHeight
    )
}