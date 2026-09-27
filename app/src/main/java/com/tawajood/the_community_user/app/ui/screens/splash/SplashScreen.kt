package com.tawajood.the_community_user.app.ui.screens.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tawajood.the_community_user.R

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navToOnBoarding: () -> Unit,
    navToAuth: () -> Unit,
    navToHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SplashEffect.NavToAuth -> navToAuth()
                SplashEffect.NavToHome -> navToHome()
                SplashEffect.NavToOnBoarding -> navToOnBoarding()
            }
        }
    }

    SplashContent()
}

@Composable
fun SplashContent() {
    val infiniteTransition = rememberInfiniteTransition(label = "flying_logo")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 12f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offsetY"
    )

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(painterResource(R.drawable.splash_background), contentScale = ContentScale.Fit, contentDescription = "")
//        Image(
//            painter = painterResource(id = R.drawable.bel),
//            contentDescription = "Game Sense Logo",
//            modifier = Modifier
//                .size(140.dp)
//                .graphicsLayer(
//                    translationY = offsetY,
//                    scaleX = scale,
//                    scaleY = scale
//                )
//        )
    }
}

/*
@Composable
fun SplashContent(
    gifResId: Int = R.raw.bel,
    durationMillis: Long = 2500,
    onFinished: () -> Unit
) {
    val context = LocalContext.current

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context)
            .data(gifResId)
            .decoderFactory(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoderDecoder.Factory()
                } else {
                    GifDecoder.Factory()
                }
            )
            .build()
    )

    LaunchedEffect(Unit) {
        delay(durationMillis)
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "Splash animation",
            modifier = Modifier.fillMaxWidth()
        )
    }
}
*/