package com.tawajood.the_community_user.app.ui.screens.society

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun PostDetailsScreen(id: Int, viewModel: PostDetailsViewModel = hiltViewModel()){
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(state.isPageLoaded) {
        if (!state.isPageLoaded){
            viewModel.sendIntent(PostDetailsIntent.LoadPage(id))
        }
    }
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is PostDetailsEffect.ShowToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(),containerColor = Color.White) {innerPadding->
        Box(modifier = Modifier.fillMaxSize()){
            LoadingScreen(state.isLoading)
        }
    }
}