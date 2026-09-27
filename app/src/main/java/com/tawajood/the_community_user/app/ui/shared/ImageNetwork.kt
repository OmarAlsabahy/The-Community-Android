package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.tawajood.the_community_user.R

@Composable
fun ImageNetwork(
    modifier: Modifier = Modifier,
    image: Any?,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Fit,
    placeholder: Int = R.drawable.ic_launcher_foreground,
    colorFilter: ColorFilter? = null,
    circularProgressColor: Color = Color.Black,
    circularProgressSize: Dp = 48.dp
) {
    SubcomposeAsyncImage(
        success = { SubcomposeAsyncImageContent(Modifier.fillMaxSize()) },
        model = ImageRequest.Builder(LocalContext.current)
            .data(image)
            .crossfade(true)
            .build(),
        loading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(circularProgressSize),
                    color = circularProgressColor
                )
            }
        },
        error = {
            Image(
                painter = painterResource(id = placeholder),
                contentDescription = "Placeholder",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )
        },
        colorFilter = colorFilter,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier.background(Color.Transparent),
    )
}