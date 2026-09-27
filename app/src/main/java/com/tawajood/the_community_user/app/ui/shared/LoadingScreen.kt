package com.tawajood.the_community_user.app.ui.shared

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tawajood.the_community_user.app.ui.theme.Primary

@Composable
fun LoadingScreen(
    state: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color.Black.copy(alpha = 0.1f),
    parentModifier: Modifier = Modifier.fillMaxSize(),
) {
    AnimatedVisibility(
        visible = state,
        enter = fadeIn(animationSpec = tween(durationMillis = 100)),
        exit = fadeOut(animationSpec = tween(durationMillis = 100))
    ) {
        Surface(
            modifier = parentModifier,
            color = color
        ) {
            Column(
                modifier = modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { }
                    )
                    .fillMaxSize()
                    .background(Color.Transparent),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(color = Primary)
            }
        }

        BackHandler { }
    }
}