package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CustomScaffold(content: @Composable (PaddingValues)-> Unit,
                   hasTopBar: Boolean = false,topBarTitle: String? = null){
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.White,
        topBar = {
            if (hasTopBar&&topBarTitle!=null){
                CustomAppBar(topBarTitle)
            }
        }) {innerPadding->
        content(innerPadding)
    }
}