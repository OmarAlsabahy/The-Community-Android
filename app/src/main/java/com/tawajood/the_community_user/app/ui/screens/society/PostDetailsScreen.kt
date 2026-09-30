package com.tawajood.the_community_user.app.ui.screens.society

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.shared.CommentCard
import com.tawajood.the_community_user.app.ui.shared.DisplayPostItem
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.app.ui.theme.BorderColor
import com.tawajood.the_community_user.app.ui.theme.Gray9E
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.Primary10
import com.tawajood.the_community_user.app.ui.theme.RedF44
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.models.society.User
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun PostDetailsScreen(id: Int, viewModel: PostDetailsViewModel = hiltViewModel(),
                      pop:()-> Unit,returnData:(PostDto?)-> Unit){
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    BackHandler() {
        returnData(state.post)
        pop()
    }

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
                is PostDetailsEffect.RevertData->{
                    returnData(effect.post)
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(),containerColor = Color.White, bottomBar = {
        Row(modifier = Modifier.padding(horizontal = 16.dp).navigationBarsPadding().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            AsyncImage(model = state.profile?.image, contentDescription = "", modifier = Modifier.size(36.dp)
                .clip(shape = CircleShape), contentScale = ContentScale.Crop)
            Row(modifier = Modifier.weight(1f).border(color = BorderColor, shape = RoundedCornerShape(10.dp),
                width = 1.dp).padding(vertical = 12.dp, horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                BasicTextField(
                    value = state.commentFieldValue,
                    onValueChange = {
                        viewModel.sendIntent(PostDetailsIntent.OnCommentFieldChanges(it))
                    },
                    modifier = Modifier.weight(1f),
                    textStyle = TextStyle(
                        fontSize = 12.sp,
                        color = Black1f
                    ),
                    decorationBox = { innerTextField ->
                        Box {
                            if (state.commentFieldValue.isEmpty()) {
                                UiText(
                                    text = "اضافة تعليق",
                                    fontSize = 12.sp,
                                    color = TextGray
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                IconButton(onClick = {
                    viewModel.sendIntent(PostDetailsIntent.SendComment)
                }) {
                    Icon(painterResource(R.drawable.send_ic), contentDescription = null, tint = Color.Unspecified)
                }
            }
        }
    }) {innerPadding->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .padding(innerPadding),
                state,
                context = context,
                onLikeClicked = {
                    viewModel.sendIntent(PostDetailsIntent.OnLikeClicked)
                },
                onBackClick = {
                    returnData(state.post)
                    pop()
                },
                onCommentLikePressed = {
                    viewModel.sendIntent(PostDetailsIntent.ChangeCommentStatus(it))
                }
            )
            LoadingScreen(state.isLoading)
        }
    }
}

@Composable
private fun DisplayContent(
    modifier: Modifier,
    state: PostDetailsUiState,
    onBackClick: () -> Unit,
    onLikeClicked: () -> Unit,
    onCommentLikePressed:(Int?)-> Unit,
    context: Context
){
    if (state.isPageLoaded){
        LazyColumn(modifier) {
            item{
                DisplayUserSection(
                    user = state.post?.user,
                    date = state.post?.created_at,
                    modifier = Modifier.fillMaxWidth(),
                    onBackClick = onBackClick
                )
            }
            item {
                UiText(
                    state.post?.content ?: "",
                    fontSize = 14.sp,
                    color = Black1f,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(top = 23.dp, bottom = 12.dp)
                )
            }
            item {
                DisplayPostImages(state.post?.media,context)
            }
            item {
                DisplayReactionsSections(Modifier.padding(top = 28.dp).fillMaxWidth(),state.post?.likeStatus , state.post?.likesCount, state.post?.commentsCount,
                    onLikeClicked = onLikeClicked)
            }
            items(items = state.post?.comments ?: emptyList()) { currentComment ->
                if (currentComment != null) {
                    CommentCard(currentComment, onLikePressed = onCommentLikePressed)
                }
            }
        }
    }
}

@Composable
private fun DisplayReactionsSections(
    modifier: Modifier,
    likeStatus: Boolean?,
    likesCount: Int?,
    commentsCount: Int?,
    onLikeClicked:()-> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly) {
        DisplayPostItem(modifier = Modifier, value = likesCount, likeStatus = likeStatus,
            icon = {
                Icon(if (likeStatus==true) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null , tint = if (likeStatus==true) RedF44 else Gray9E,
                    modifier = Modifier.size(20.dp)
                )
            }, onClick = onLikeClicked)
        DisplayPostItem(Modifier,commentsCount,null,icon={
            Icon(painterResource(R.drawable.comment_ic) , contentDescription = null,
                modifier = Modifier.size(20.dp), tint = Color.Unspecified)
        }, onClick ={})
        DisplayPostItem(Modifier,null,null, icon = {
            Icon(painterResource(R.drawable.share_ic) , contentDescription = null,
                modifier = Modifier.size(20.dp), tint = Color.Unspecified)
        }, onClick = {})
    }
}

@Composable
fun DisplayDotsIndicator(modifier: Modifier, pagerState: PagerState, mediaSize: Int) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        repeat(mediaSize){index->
            val isSelected = index == pagerState.currentPage
            Box(
                Modifier
                    .padding(end = if (index < mediaSize - 1) 5.dp else 0.dp)
                    .width(if (isSelected) 16.dp else 6.dp)
                    .height(6.dp)
                    .background(
                        color = if (!isSelected) Primary10 else Primary,
                        shape = if (!isSelected) CircleShape else RoundedCornerShape(100.dp)
                    )
            )
        }
    }
}

@Composable
private fun DisplayPostImages(media: List<String?>?, context: Context) {
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .build()
    }
    if (!media.isNullOrEmpty()){
        val pagerState = rememberPagerState { media.size }
        Box(modifier = Modifier.fillMaxWidth().aspectRatio(393f / 200f)){
            HorizontalPager(pagerState, modifier = Modifier.fillMaxSize()) { index ->
                val currentMedia = media[index]
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(currentMedia)
                            .videoFrameMillis(1000)
                            .crossfade(true)
                            .build(),
                        imageLoader = imageLoader,
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Box(
                Modifier
                    .padding(top = 12.dp, end = 12.dp)
                    .background(color = Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ){
                UiText(
                    "${pagerState.currentPage + 1} / ${media.size}",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center
                )
            }
        }
        DisplayDotsIndicator(Modifier.fillMaxWidth().padding(top = 8.dp), pagerState, media.size)
    }
}

@Composable
private fun DisplayUserSection(
    user: User?,
    date: String?,
    modifier: Modifier,
    onBackClick: () -> Unit
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            IconButton(onClick = onBackClick) {
                Icon(
                    painterResource(R.drawable.back_ic),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                AsyncImage(
                    model = user?.image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(shape = CircleShape)
                )
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    UiText(user?.name ?: "", fontSize = 16.sp, color = Black1f, fontWeight = FontWeight.W700)
                    UiText(date ?: "", fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.W400)
                }
            }
        }

        Icon(
            painterResource(R.drawable.more_vertical_ic),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
    }
}