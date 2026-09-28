package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.theme.Gray777
import com.tawajood.the_community_user.app.ui.theme.GrayF5
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.domain.models.home.BottomNavBarItems

@Composable
fun CustomBottomAppBar(onItemClicked:(AppRoutes?)-> Unit){
    val strings = LocalStrings.current
    val items = listOf(
        BottomNavBarItems.Home(strings.home),
        BottomNavBarItems.Society(strings.society),
        BottomNavBarItems.Scan(strings.scan),
        BottomNavBarItems.Notification(strings.notification),
        BottomNavBarItems.Profile(strings.profile)
    )
    val selectedItem = remember {
        mutableStateOf(items[0])
    }
    Box(modifier = Modifier.fillMaxWidth()){
        Row(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                CustomBottomNavItem(item, selectedItem.value, Modifier.weight(1f), onItemClicked = {route->
                    if (selectedItem.value!=item){
                        onItemClicked(route)
                        selectedItem.value = item
                    }
                })
            }
        }
        DisplayScanItem(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .background(color = GrayF5, shape = CircleShape)
                .padding(20.dp)
        )
    }
}

@Composable
private fun DisplayScanItem(modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        Icon(
            painterResource(R.drawable.scan_ic),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Gray777
        )
    }
}

@Composable
private fun CustomBottomNavItem(
    item: BottomNavBarItems,
    selectedItem: BottomNavBarItems,
    modifier: Modifier,
    onItemClicked:(AppRoutes?)-> Unit
) {
    val isSelected = item == selectedItem
    Box(modifier = modifier.clickable{
        onItemClicked(item.route)
    }){
        if (isSelected){
            Box(modifier = Modifier.width(32.dp).aspectRatio(32f/4f)
                .background(color = Primary, shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                .align(Alignment.TopCenter))
        }
        if (item !is BottomNavBarItems.Scan){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(top = 12.dp).align(Alignment.Center)
            ) {
                Icon(
                    painterResource(item.icon),
                    tint = if (isSelected) Primary else Gray777,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                UiText(
                    item.title,
                    fontSize = 12.sp,
                    color = if (isSelected) Primary else Gray777,
                    fontWeight = if (isSelected) FontWeight.W700 else FontWeight.W400,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}