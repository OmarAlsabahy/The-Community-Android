package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.navigation.LocalNavigationProvider
import com.tawajood.the_community_user.app.ui.theme.Black1f

@Composable
fun CustomAppBar(title: String){
    val controller = LocalNavigationProvider.current
    Row(modifier = Modifier.fillMaxWidth().statusBarsPadding(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        IconButton(onClick = {
            controller.popBackStack()
        }) {
            Icon(painterResource(R.drawable.back_ic),contentDescription = null,
                tint = Color.Unspecified)
        }
        UiText(title, fontSize = 16.sp, color = Black1f, fontWeight = FontWeight.W700)
    }
}