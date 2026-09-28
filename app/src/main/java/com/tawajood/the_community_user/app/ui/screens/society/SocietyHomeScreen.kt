package com.tawajood.the_community_user.app.ui.screens.society

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.screens.home.TopHeaderButton
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.app.ui.shared.PostCard
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.Primary40
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun SocietyHomeScreen(viewModel: SocietyHomeViewModel = hiltViewModel(),
                      nav:(AppRoutes)-> Unit){
    val strings = LocalStrings.current
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is SocietyHomeEffect.ShowToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
                is SocietyHomeEffect.Nav->{
                    nav(effect.route)
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.White, topBar = {
        DisplayTopBar(Modifier.fillMaxWidth(),strings.society)
    }) {innerPadding->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
                .fillMaxSize().padding(innerPadding),
                state, onItemClicked = {index->
                    viewModel.sendIntent(SocietyHomeIntent.ChangeCategory(index))
                }, onFavClicked = {id->
                    viewModel.sendIntent(SocietyHomeIntent.ChangePostLike(id))
                },
                {id->
                    viewModel.sendIntent(SocietyHomeIntent.OnPostClicked(id))
                })
            LoadingScreen(state.isLoading)
        }
    }
}

@Composable
private fun DisplayContent(modifier: Modifier, state: SocietyHomeUiState,
                           onItemClicked: (Int) -> Unit,
                           onFavClicked: (Int?) -> Unit,
                           onCardClicked:(Int?)-> Unit) {
    Column(modifier, verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Image(painterResource(R.drawable.banner), contentDescription = null, modifier = Modifier.fillMaxWidth()
            .aspectRatio(361f/160f).clip(shape = RoundedCornerShape(20.dp)), contentScale = ContentScale.Crop)
        DisplayCategories(Modifier.padding(top = 8.dp).fillMaxWidth(),state.categories,
            state.selectedCategory, onItemClicked = onItemClicked)
        DisplayPosts(Modifier.fillMaxWidth().weight(1f),state.posts, onFavClicked = onFavClicked,
            onItemClicked = onCardClicked)
    }
}

@Composable
private fun DisplayPosts(modifier: Modifier, posts: List<PostDto>,onFavClicked:(Int?)-> Unit,
                         onItemClicked: (Int?) -> Unit) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        items(posts.size) { index ->
            val currentPost = posts[index]
            PostCard(currentPost, onFavClicked = onFavClicked, onCardClicked = onItemClicked)
        }
    }
}

@Composable
private fun DisplayCategories(
    modifier: Modifier,
    categories: List<PostCategoriesDto>,
    selectedCategory: Int,onItemClicked:(Int)-> Unit
) {
    LazyRow(modifier , horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(categories.size){index->
            val isSelected = index == selectedCategory
            val currentCategory = categories[index]
            CategoryItem(currentCategory.name,isSelected, onItemClicked = {
                onItemClicked(index)
            })
        }
    }
}

@Composable
private fun CategoryItem(name: String?, isSelected: Boolean, onItemClicked: () -> Unit) {
    if (name != null) {
        Column(
            modifier = Modifier
                .clickable(enabled = true, onClick = onItemClicked)
                .width(IntrinsicSize.Max),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UiText(
                text = name,
                fontSize = 14.sp,
                color = if (!isSelected) Primary40 else Primary,
                fontWeight = if (!isSelected) FontWeight.W400 else FontWeight.W700,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            color = Primary,
                            shape = RoundedCornerShape(bottomEnd = 10.dp, bottomStart = 10.dp)
                        )
                )
            } else {
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
}

@Composable
private fun DisplayTopBar(modifier: Modifier, title: String) {
    Box(modifier){
        UiText(title , fontSize = 18.sp , color = Black1f , fontWeight = FontWeight.W700,
            textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
        TopHeaderButton(R.drawable.notification_ic) { }
    }
}