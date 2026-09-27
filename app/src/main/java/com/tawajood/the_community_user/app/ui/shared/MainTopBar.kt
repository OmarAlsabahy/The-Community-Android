package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    onClickBack: () -> Unit
) {
    TopAppBar(
        title = {
            UiText(
                text = title,
                fontSize = 16.sp
            )
        },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onClickBack()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .rotate(if (LocalLayoutDirection.current == LayoutDirection.Ltr) 0F else 180F)
                        .padding(8.dp)
                        .size(24.dp),
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "back",
                    tint = Color.Black,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White
        )
    )
}

@Preview
@Composable
private fun MainTopBarPreview() {
    MainTopBar("") {}
}