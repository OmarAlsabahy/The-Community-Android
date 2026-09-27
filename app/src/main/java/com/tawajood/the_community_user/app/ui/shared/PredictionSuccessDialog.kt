package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.language.LocalStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionSuccessDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    val strings = LocalStrings.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
    ) {
        Dialog(
            onDismissRequest = onDismiss
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "close",
                            tint = Color.Black
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = "checked",
                        modifier = Modifier
                            .size(100.dp),
                    )
                    Spacer(modifier = Modifier)
                    UiText(
                        text = strings.yourPredictionHasBeenSubmitted,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    UiText(
                        text = strings.yourPredictionHasBeenSubmitted,
                        color = Color.Gray
                    )
                    AppButton(
                        title = strings.returnToTheHomeScreen,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp), onClick = {
                            onConfirm()
                        })
                    Spacer(modifier = Modifier)
                }
            }
        }
    }
}
