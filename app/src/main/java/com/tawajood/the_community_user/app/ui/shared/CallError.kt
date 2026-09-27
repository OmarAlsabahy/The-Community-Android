package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.domain.base.NetworkError

@Composable
fun CallError(
    state: Boolean,
    error: String?,
    errorType: NetworkError?,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    val strings = LocalStrings.current
    AnimatedVisibility(
        visible = state
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            UiText(
                text = if (errorType == NetworkError.NO_INTERNET) {
                    strings.noInternet
                } else {
                    error ?: ""
                },
                textAlign = TextAlign.Center,
                color = Color.Red,
                modifier = Modifier
                    .padding(8.dp),
            )
            AppButton(
                title = strings.tryAgain,
                isEnabled = state,
            onClick = {
                onRetry()
            })
        }
    }
}
