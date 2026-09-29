package com.tawajood.the_community_user.app.ui.screens.society

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.tawajood.the_community_user.app.ui.shared.DisplayPostItem
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Gray9E
import com.tawajood.the_community_user.app.ui.theme.RedF44
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun PostImageScreen(
    post: PostDto,
    viewModel: PostImageViewModel = hiltViewModel(),
    returnData:(PostDto?)-> Unit,
    pop:()-> Unit,
    nav:(AppRoutes)-> Unit
){
    val state by viewModel.state.collectAsState()
    val images = state.post?.media?.filterNotNull()?:emptyList()
    val pagerState = rememberPagerState() { images.size }
    val context = LocalContext.current
    LaunchedEffect(state.isPageLoaded) {
        if (!state.isPageLoaded){
            viewModel.sendIntent(PostImageIntent.loadPage(post))
        }
    }
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is PostImageEffect.ShowToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
                is PostImageEffect.Nav->{
                    nav(effect.route)
                }
            }
        }
    }
    BackHandler() {
        returnData(state.post)
        pop()
    }
    Scaffold(modifier = Modifier.fillMaxWidth(), containerColor = Color(0xFF141718), bottomBar = {
        DisplayUserData(post = state.post, onLikeClicked = {
            viewModel.sendIntent(PostImageIntent.ChangeLikeStatus)
        }, onCommentClicked = {
            viewModel.sendIntent(PostImageIntent.OnCommentClicked)
        })
    }) { innerPadding->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Center) {
            DisplayImages(pagerState, images)
        }
    }
}

@Composable
private fun DisplayUserData(post: PostDto?, onLikeClicked: () -> Unit,
                            onCommentClicked: () -> Unit) {
    if (post!=null){
        Column(Modifier.navigationBarsPadding().padding(horizontal = 16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AsyncImage(model = post.user?.image , modifier = Modifier.size(36.dp)
                    .clip(shape = CircleShape), contentScale = ContentScale.Crop, contentDescription = null)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    UiText(post.user?.name?:"" , fontSize = 16.sp , color = Color.White, fontWeight = FontWeight.W700)
                    UiText(post.created_at?:"" , fontSize = 10.sp , color = TextGray, fontWeight = FontWeight.W400)
                }
            }
            UiText(
                post.content?:"" ,
                fontSize = 14.sp ,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
            DisplayReactionsSections(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 12.dp)
                    .fillMaxWidth(),
                likeStatus = post.likeStatus,
                likesCount = post.likesCount,
                commentsCount = post.commentsCount,
                onLikeClicked = onLikeClicked,
                onCommentClicked = onCommentClicked
            )
        }
    }
}

@Composable
private fun DisplayReactionsSections(
    modifier: Modifier,
    likeStatus: Boolean?,
    likesCount: Int?,
    commentsCount: Int?,
    onLikeClicked: () -> Unit,
    onCommentClicked:()-> Unit={}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DisplayPostItem(
            modifier = Modifier,
            value = likesCount,
            likeStatus = likeStatus,
            icon = {
                Icon(
                    if (likeStatus == true) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = if (likeStatus == true) RedF44 else Gray9E,
                    modifier = Modifier.size(20.dp)
                )
            },
            onClick = onLikeClicked,
            textColor = if (likeStatus == true) Color.White else TextGray
        )
        DisplayPostItem(
            modifier = Modifier,
            value = commentsCount,
            likeStatus = null,
            icon = {
                Icon(
                    painterResource(R.drawable.comment_ic),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
            },
            onClick = onCommentClicked,
            textColor = TextGray
        )
        DisplayPostItem(
            modifier = Modifier,
            value = null,
            likeStatus = null,
            icon = {
                Icon(
                    painterResource(R.drawable.share_ic),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
            },
            onClick = {},
            textColor = TextGray
        )
    }
}

@Composable
private fun DisplayImages(state: PagerState, images: List<String>) {
    HorizontalPager(state,modifier = Modifier.fillMaxWidth().aspectRatio(1f)) {index->
        Box(Modifier.fillMaxSize()){
            val currentImage = images[index]
            AsyncImage(model = currentImage , contentDescription = null, modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop)
            Box(
                Modifier
                    .padding(top = 12.dp, end = 12.dp)
                    .background(color = Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ){
                UiText(
                    "${state.currentPage + 1} / ${images.size}",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center
                )
            }
            DisplayDotsIndicator(modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth()
                .align(Alignment.BottomCenter),state,images.size)
        }
    }
}