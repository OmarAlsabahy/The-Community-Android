package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.app.ui.theme.Primary

@Composable
fun AppButton(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Primary,
    textColor: Color = White,
    strokeWidth: Dp = 0.dp,
    fontSize: TextUnit = 16.sp,
    strokeColor: Color = Color.Unspecified,
    isEnabled: Boolean = true,
    shape: Shape = RoundedCornerShape(16.dp),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    onClick: () -> Unit,
    paddingValues: PaddingValues = PaddingValues(vertical = 12.dp),
    fontWeight: FontWeight = FontWeight.W400
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = shape,
        border = BorderStroke(strokeWidth, strokeColor),
        enabled = isEnabled,
        contentPadding = paddingValues,
        colors = ButtonDefaults.buttonColors(
            contentColor = textColor,
            containerColor = backgroundColor,
            disabledContainerColor = backgroundColor.copy(alpha = 0.20f)
        ),
        elevation = elevation,
    )
    {
        UiText(
            text = title,
            fontSize = fontSize,
            color = textColor,
            modifier = Modifier.padding(vertical = 4.dp),
            fontWeight = fontWeight
        )
    }
}