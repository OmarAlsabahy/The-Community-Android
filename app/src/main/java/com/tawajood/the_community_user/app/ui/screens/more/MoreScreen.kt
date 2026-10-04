package com.tawajood.the_community_user.app.ui.screens.more

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.app.language.Strings
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.shared.CustomScaffold
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Alert
import com.tawajood.the_community_user.app.ui.theme.GrayFa
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.domain.models.more.MoreItems
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel

@Composable
fun MoreScreen(viewModel: MoreViewModel = hiltViewModel(),nav:(AppRoutes)-> Unit){
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val strings = LocalStrings.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is MoreEffect.Nav->{
                    nav(effect.route)
                }
            }
        }
    }
    CustomScaffold(hasTopBar = false, content = {paddingValues->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.fillMaxSize()
                .verticalScroll(scrollState),state,strings, onItemClicked = {route->
                    viewModel.sendIntent(MoreIntent.OnItemClicked(route))
            })
        }
    })
}

@Composable
private fun DisplayContent(modifier: Modifier, state: MoreUiState,strings: Strings,
                           onItemClicked: (AppRoutes?) -> Unit) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(32.dp)) {
        DisplayProfile(state.profile)
        DisplaySection(strings.personalInformation,state.personalInformationItems,strings,
            onItemClicked)
        DisplaySection("الاعدادات والخصوصية",state.settingsItems,strings,onItemClicked)
        DisplaySection("",state.logoutItems,strings,onItemClicked)
    }
}

@Composable
private fun DisplaySection(title: String, items: List<MoreItems>, strings: Strings,
                           onItemClicked: (AppRoutes?) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(GrayFa)) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            if (title.isNotEmpty()){
                UiText(title, fontSize = 16.sp, color = Primary, fontWeight = FontWeight.W700)
            }
            repeat(items.size){index->
                val item = items[index]
                MoreItemCard(item,strings,onItemClicked)
            }
        }
    }
}

@Composable
private fun MoreItemCard(item: MoreItems, strings: Strings,onItemClicked:(AppRoutes?)-> Unit) {
    Row(modifier = Modifier.fillMaxWidth().clickable{
        onItemClicked(item.route)
    }, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(shape = CircleShape, colors = CardDefaults.cardColors(Color(0xFFeceef0))) {
                Icon(painter = painterResource(item.icon), contentDescription = "",
                    modifier = Modifier.padding(10.dp).size(24.dp), tint = if (item !is MoreItems.Logout) TextGray else Alert
                )
            }
            UiText(item.title(strings) , fontSize = 14.sp, color = TextGray)
        }
        when(item){
            is MoreItems.Logout -> {}
            is MoreItems.Notifications->{}
            is MoreItems.Language->{}
            else -> {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight,contentDescription = "", tint = TextGray,
                    modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Composable
private fun DisplayProfile(profile: ProfileResponseModel?) {
    if (profile!=null){
        Box(modifier = Modifier.fillMaxWidth().aspectRatio(393f/172f).background(
            brush = Brush.linearGradient(colors = listOf(Color(0xFF063E46),Color(0xFF1A1F50) ),
                start = Offset(0f, Float.POSITIVE_INFINITY),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)),
            shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
            contentAlignment = Alignment.Center
        ){
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(model = profile.name?:"", modifier = Modifier.size(56.dp).clip(CircleShape),
                    contentDescription = "", contentScale = ContentScale.Crop)
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    UiText(profile.name?:"" , fontSize = 18.sp, fontWeight = FontWeight.W700,
                        color = Color.White)
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        UiText("${profile.countryCode}${profile.phone}", fontSize = 14.sp, color = Color.White)
                    }
                }
            }
        }
    }
}