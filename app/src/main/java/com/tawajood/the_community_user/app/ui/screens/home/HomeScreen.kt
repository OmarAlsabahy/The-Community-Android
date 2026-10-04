package com.tawajood.the_community_user.app.ui.screens.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.shared.CustomScaffold
import com.tawajood.the_community_user.app.ui.shared.SearchField
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Gray50
import com.tawajood.the_community_user.app.ui.theme.Gray900
import com.tawajood.the_community_user.app.ui.theme.GrayF4
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.domain.models.home.AnnouncementModelDto
import com.tawajood.the_community_user.domain.models.home.BannerModel
import com.tawajood.the_community_user.domain.models.home.HomeCategories
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel
import com.tawajood.the_community_user.utils.ToastUtils
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(),nav:(AppRoutes)-> Unit){
    val state by  viewModel.state.collectAsState()
    val context = LocalContext.current
    val pagerState = rememberPagerState(pageCount = {state.banners.size})
    val categories = listOf(
        HomeCategories.Permission("التصاريح"),
        HomeCategories.Maintenance("الصيانة"),
        HomeCategories.Guide("الدليل"),
        HomeCategories.Payments("المدفوعات"),
        HomeCategories.CommunitySettings("خدمات المجمع السكني"),
        HomeCategories.Community("The Community"),
        HomeCategories.Rents("الايجارات"),
        HomeCategories.Help("المساعدة")
    )
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect->
            when(effect){
                is HomeEffect.ShowToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
                is HomeEffect.Nav->{
                    nav(effect.route)
                }
            }
        }
    }
    LaunchedEffect(pagerState.currentPage) {
        if (!pagerState.isScrollInProgress && pagerState.currentPage<state.banners.size-1){
            delay(2000.milliseconds)
            pagerState.animateScrollToPage(pagerState.currentPage+1)
        }
    }

    CustomScaffold(hasTopBar = false, floatActionButton = {
        Box(modifier = Modifier.background(color = Primary, shape = CircleShape).padding(14.dp),
            contentAlignment = Alignment.Center){
            Icon(Icons.Filled.Add, modifier = Modifier.size(28.dp), contentDescription = null,
                tint = Color.White)
        }
    }, content = {innerPadding->

        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(horizontal = 16.dp).fillMaxSize()
                .padding(innerPadding),
                state, onSearchValueChange = {value->
                    viewModel.sendIntent(HomeIntent.OnSearchValueChanges(value))
                },pagerState,categories, onCategoryClicked = {route->
                    viewModel.sendIntent(HomeIntent.OnCategoryClicked(route))
                })
        }
    })
}

@Composable
private fun DisplayContent(
    modifier: Modifier,
    state: HomeUiState,
    onSearchValueChange: (String) -> Unit = {},
    pagerState: PagerState,
    categories: List<HomeCategories>,
    onCategoryClicked:(AppRoutes?)-> Unit
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(4), verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        item(span = { GridItemSpan(maxLineSpan)}) {
            DisplayProfileSection(Modifier.fillMaxWidth(),state.profile)
        }
//        item(span = {GridItemSpan(maxLineSpan)}){
//            SearchField(state.searchValue,onSearchValueChange)
//        }
        item(span = { GridItemSpan(maxLineSpan)}){
            DisplayBanners(state.banners,pagerState)
        }
        item (span = { GridItemSpan(maxLineSpan)}){
            UiText("الاقسام", fontSize = 24.sp, color = Gray900, fontWeight = FontWeight.W700)
        }
        items(categories.size){index->
            val category = categories[index]
            Column(verticalArrangement = Arrangement.spacedBy(12.dp), horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().clickable{
                    onCategoryClicked(category.route)
                }) {
                Image(painterResource(category.icon), contentDescription = null, modifier = Modifier.fillMaxWidth().aspectRatio(1f))
                UiText(category.title , fontSize = 12.sp, color = Gray50, fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center)
            }
        }
        item(span = { GridItemSpan(maxLineSpan)}){
            DisplayAnnouncement(Modifier.fillMaxWidth().aspectRatio(361f/189f),state.announcement)
        }
    }
}

@Composable
private fun DisplayAnnouncement(modifier: Modifier, announcement: AnnouncementModelDto?) {
    if (announcement!=null){
        Card(modifier, shape = RoundedCornerShape(16.dp)) {
            AsyncImage(model = announcement.image , modifier = Modifier.fillMaxSize(),
                contentDescription = null , contentScale = ContentScale.Crop)
        }
    }
}

@Composable
private fun DisplayBanners(banners: List<BannerModel>, pagerState: PagerState,) {
    if (banners.isNotEmpty()){
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalPager(pagerState, pageSpacing = 8.dp) {index->
                AsyncImage(model = banners[index].image , contentDescription = null,
                    modifier = Modifier.fillMaxWidth().aspectRatio(340f/189f)
                        .clip(shape = RoundedCornerShape(24.dp)), contentScale = ContentScale.Crop)
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                Row( horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    repeat(banners.size){
                        Box(modifier = if (it == pagerState.currentPage){
                            Modifier.width(55.dp).aspectRatio(55f/15f).background(color = Primary,
                                shape = RoundedCornerShape(8.dp))
                        }else{
                            Modifier.width(17.dp).aspectRatio(17f/10f).background(color = Color(0xFFE9EDF1),
                                shape = RoundedCornerShape(8.dp))
                        }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DisplayProfileSection(modifier: Modifier, profile: ProfileResponseModel?) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.Top) {
            AsyncImage(model = profile?.image , contentDescription = null,
                modifier = Modifier.size(40.dp).clip(shape = CircleShape))
            UiText(profile?.name?:"", fontSize = 14.sp, color = Primary, fontWeight = FontWeight.W700)
        }

        Row(verticalAlignment = Alignment.CenterVertically , horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            TopHeaderButton(icon = R.drawable.qr_code_ic){}
            TopHeaderButton(icon = R.drawable.notification_ic){}
        }
    }
}

@Composable
fun TopHeaderButton(modifier: Modifier= Modifier,icon: Int, onButtonClicked: () -> Unit) {
    Card(modifier = modifier,onClick = onButtonClicked, shape = CircleShape,
        colors = CardDefaults.cardColors(GrayF4)) {
        Icon(painterResource(icon), modifier = Modifier.padding(11.dp),contentDescription = null,
            tint = Color.Unspecified)
    }
}