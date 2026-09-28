package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.app.ui.theme.Gray9E
import com.tawajood.the_community_user.app.ui.theme.Primary10
import com.tawajood.the_community_user.app.ui.theme.RedF44
import com.tawajood.the_community_user.app.ui.theme.Secondary
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.domain.models.society.User

@Composable
fun PostCard(post: PostDto,onFavClicked:(Int?)-> Unit,onCardClicked:(Int?)-> Unit){
    val pagerState = rememberPagerState() {post.media?.size?:0 }
    Card(onClick = {onCardClicked(post.id)},modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        border = BorderStroke(width = 1.dp , color = Primary10), colors = CardDefaults.cardColors(Color.White)) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
            DisplayUserSection(Modifier.fillMaxWidth(),post.user,post.created_at?:"")
            DisplayPostContent(Modifier.fillMaxWidth(),post.content?:"",post.category?:"",
                post.media,pagerState)
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp).fillMaxWidth(), color = Primary10)
            DisplayLikesComments(Modifier.fillMaxWidth(),post, onFavClicked = {
                onFavClicked(post.id)
            }, onCommentClicked = {onCardClicked(post.id)})
        }
    }
}

@Composable
private fun DisplayLikesComments(modifier: Modifier, posts: PostDto,onFavClicked: () -> Unit,
                                 onCommentClicked:()-> Unit) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        Row(verticalAlignment = Alignment.CenterVertically) {
            DisplayPostItem(Modifier.weight(1f),posts.likesCount,posts.likeStatus,{
                Icon(if (posts.likeStatus==true) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = null , tint = if (posts.likeStatus==true) RedF44 else Gray9E,
                    modifier = Modifier.size(20.dp)
                )
            }, onClick = onFavClicked)
            DisplayPostItem(Modifier.weight(1f),posts.commentsCount,null,icon={
                Icon(painterResource(R.drawable.comment_ic) , contentDescription = null,
                    modifier = Modifier.size(20.dp), tint = Color.Unspecified)
            }, onClick = onCommentClicked)
            DisplayPostItem(Modifier.weight(1f),null,null, icon = {
                Icon(painterResource(R.drawable.share_ic) , contentDescription = null,
                    modifier = Modifier.size(20.dp), tint = Color.Unspecified)
            }, onClick = {})
        }
        DisplayPostItem(value = null, likeStatus = null, icon = {
            Icon(painterResource(R.drawable.bookmark_ic) , contentDescription = null,
                modifier = Modifier.size(20.dp), tint = Color.Unspecified)
        }, onClick = {})
    }
}

@Composable
private fun DisplayPostItem(
    modifier: Modifier= Modifier,
    value: Int?,
    likeStatus: Boolean?,
    icon: @Composable () -> Unit,
    onClick:()-> Unit
) {
    Row(modifier.clickable(enabled = true, onClick = onClick),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        icon()
        if (value!=null){
            UiText(value.toString() , fontSize = 12.sp , color = if (likeStatus!=null && likeStatus) Black1f else TextGray)

        }
    }
}

@Composable
private fun DisplayPostContent(
    modifier: Modifier,
    content: String,
    category: String,
    media: List<String?>?,
    pagerState: PagerState
) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Column(modifier = Modifier.weight(2.5f), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            UiText(content, fontSize = 14.sp , fontWeight = FontWeight.W400 , color = Black1f)
            Box(modifier = Modifier.background(color = Primary10 , shape = RoundedCornerShape(99.dp))
                .padding(vertical = 2.dp, horizontal = 16.dp), contentAlignment = Alignment.Center){
                UiText(category, fontSize = 10.sp, color = Black1f)
            }
        }
        HorizontalPager(pagerState, modifier = Modifier.weight(1f).aspectRatio(93f/96f)) { index->
            val currentMedia = media?.get(index)
            Box(Modifier.fillMaxSize().clip(shape = RoundedCornerShape(4.dp))){
                AsyncImage(model = currentMedia , contentDescription = null, modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit)
                Box(Modifier.padding(start = 4.dp, top = 4.dp).background(color = Color.White.copy(alpha = 0.28f), shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 6.5.dp).align(Alignment.TopEnd), contentAlignment = Alignment.Center){
                    UiText("${pagerState.currentPage+1} / ${media?.size}", fontSize = 11.sp , color = Color.White,
                        fontWeight = FontWeight.W700)
                }
                DisplayDotsIndicator(Modifier.fillMaxWidth().align(Alignment.BottomCenter),pagerState, media?.size?:0)
            }
        }
    }
}

@Composable
private fun DisplayDotsIndicator(modifier: Modifier,pagerState: PagerState, mediaSize: Int) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        repeat(mediaSize){index->
            val isSelected = index == pagerState.currentPage
            Box(Modifier.padding(end = if (index<mediaSize-1) 5.dp else 0.dp).width(if (isSelected) 16.dp else 6.dp).height(6.dp).background(color = if (!isSelected) Primary10 else Secondary
                , shape = if (!isSelected) CircleShape else RoundedCornerShape(100.dp)))
        }
    }
}

@Composable
private fun DisplayUserSection(modifier: Modifier, user: User?, createdAt: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AsyncImage(user?.image , contentDescription = null , modifier = Modifier.size(36.dp)
                .clip(shape = CircleShape), contentScale = ContentScale.Crop)
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)){
                UiText(user?.name?:"" , fontSize = 16.sp , color = Black1f , fontWeight = FontWeight.W700)
                UiText(createdAt, fontSize = 10.sp, color = TextGray)
            }
        }
        IconButton(onClick = {}) {
            Icon(painterResource(R.drawable.more_ic),contentDescription = null, tint = Color.Unspecified)
        }
    }
}