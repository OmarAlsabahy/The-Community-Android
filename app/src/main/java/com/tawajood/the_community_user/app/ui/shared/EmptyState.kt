package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    imageRes: Int,
    title: String,
    description: String?,
    buttonTitle: String?,
    onButtonClick: () -> Unit = {},
    imageSize: Dp = 140.dp,
    contentPadding: PaddingValues = PaddingValues(horizontal = 24.dp),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(contentPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier.size(imageSize),
            contentScale = ContentScale.Fit
        )
        UiText(
            text = title,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )

        description?.let {
            Spacer(Modifier.height(8.dp))
            UiText(
                text = description,
                fontSize = 10.sp,
                color = Color(0xFF7A7A7A),
                textAlign = TextAlign.Center
            )
        }
        buttonTitle?.let {
            Button(
                onClick = onButtonClick,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                UiText(
                    text = buttonTitle,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}
