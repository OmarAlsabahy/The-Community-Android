package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tawajood.the_community_user.app.base.DataState
import com.tawajood.the_community_user.app.base.isErrorState
import com.tawajood.the_community_user.app.base.isLoadingState
import com.tawajood.the_community_user.app.base.isSuccessState

@Composable
fun ScreenContentHandler(
    state: DataState<*>,
    loadingContent: @Composable () -> Unit,
    screenContent: @Composable () -> Unit,
    onRetry: () -> Unit,
) {
    when {
        state.isSuccessState() -> {
            screenContent()
        }

        state.isLoadingState() -> {
            loadingContent()
        }

        state.isErrorState() -> {
            CallError(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                state = state.isErrorState(),
                error = state.errorMessage,
                errorType = state.error,
                onRetry = onRetry
            )
        }
    }
}

fun LazyListScope.screenContentHandler(
    state: DataState<*>,
    loadingContent: LazyListScope.() -> Unit,
    screenContent: LazyListScope.() -> Unit,
    onRetry: () -> Unit,
) {
    when {
        state.isSuccessState() -> {
            screenContent()
        }

        state.isLoadingState() -> {
            loadingContent()
        }

        state.isErrorState() -> {
            item {
                CallError(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    state = state.isErrorState(),
                    error = state.errorMessage,
                    errorType = state.error,
                    onRetry = onRetry
                )
            }
        }
    }
}
