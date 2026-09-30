package com.tawajood.the_community_user.app.ui.screens.society

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.shared.AppButton
import com.tawajood.the_community_user.app.ui.shared.CustomScaffold
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.app.ui.theme.GrayF2
import com.tawajood.the_community_user.app.ui.theme.GrayFa
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.Primary50
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.app.ui.theme.TextPrimary
import com.tawajood.the_community_user.domain.models.profile.ProfileResponseModel
import com.tawajood.the_community_user.domain.models.society.PostCategoriesDto
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun AddPostScreen(viewModel: AddPostViewModel = hiltViewModel(),pop:(PostDto?)-> Unit){
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {uris->
        if (uris!=null){
            viewModel.sendIntent(AddPostIntent.AddImages(uris))
        }
    }
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is AddPostEffect.ShowToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
                is AddPostEffect.PickImages->{
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }
                is AddPostEffect.Pop->{
                    pop(effect.post)
                }
            }
        }
    }
    CustomScaffold(topBarTitle = "اضافة منشور" , hasTopBar = true, bottomAppBar = {
        Box(modifier = Modifier.fillMaxWidth()){
            AppButton(title = "نشر", fontSize = 16.sp , textColor = Color.White ,
                shape = RoundedCornerShape(12.dp), paddingValues = PaddingValues(vertical = 12.dp,
                    horizontal = 52.dp), onClick = {
                        viewModel.sendIntent(AddPostIntent.OnSubmitPressed)
                    }
                , modifier = Modifier.padding(16.dp).align(Alignment.CenterEnd),
                isEnabled = state.isButtonEnabled)
        }
    }, content = {paddingValues->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(horizontal = 16.dp).fillMaxSize().padding(paddingValues),
                state,context, onCategoryClicked = {category->
                    viewModel.sendIntent(AddPostIntent.OnCategoryClicked(category))
                }, onPostValueChanges = {value->
                    viewModel.sendIntent(AddPostIntent.OnPostValueChanges(value))
                }, onImagePickerClicked = {
                    viewModel.sendIntent(AddPostIntent.OnImagerPickerClicked)
                }, onDeleteImage = {uri->
                    viewModel.sendIntent(AddPostIntent.OnDeleteImage(uri))
                })
            LoadingScreen(state.isLoading)
        }
    })
}
@Composable
fun DisplayContent(
    modifier: Modifier,
    state: AddPostUiState,
    context: Context,
    onCategoryClicked: (PostCategoriesDto) -> Unit,
    onPostValueChanges: (String) -> Unit,
    onImagePickerClicked: () -> Unit,
    onDeleteImage:(Uri)-> Unit
) {
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(VideoFrameDecoder.Factory())
            }
            .build()
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            DisplayProfile(state.profile)
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            DisplayPostsCategories(
                state.postsCategories,
                state.selectedCategory,
                onCategoryClicked = onCategoryClicked
            )
        }
        item(span = { GridItemSpan(maxLineSpan) }) {
            DisplayPostField(state.postFieldValue, onValueChanges = onPostValueChanges)
        }
        item {
            Box(
                modifier = Modifier
                    .size(112.dp)
                    .background(color = GrayFa, shape = RoundedCornerShape(8.dp))
                    .clickable(enabled = true, onClick = onImagePickerClicked)
                    .drawBehind {
                        drawRoundRect(
                            color = Color(0xFFE4E7E9),
                            style = Stroke(
                                width = 2.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(8.dp.toPx(), 8.dp.toPx()),
                                    phase = 0f
                                )
                            ),
                            cornerRadius = CornerRadius(8.dp.toPx())
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(13.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painterResource(R.drawable.add_image_ic),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    UiText("أضف صورة", fontSize = 10.sp, color = TextPrimary)
                }
            }
        }
        items(state.mediaImages.size) { index ->
            val currentImage = state.mediaImages[index]
            Box(
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(shape = RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(currentImage)
                        .videoFrameMillis(1000)
                        .crossfade(true)
                        .build(),
                    imageLoader = imageLoader,
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier.matchParentSize()
                )
                Box(Modifier.padding(horizontal = 17.dp, vertical = 6.dp).background(color = Primary50 , shape = CircleShape)
                    .padding(5.dp).clickable(enabled = true, onClick = {onDeleteImage(currentImage)})
                    .align(Alignment.TopEnd), contentAlignment = Alignment.Center){
                    Icon(painterResource(R.drawable.delete_ic),contentDescription = null,
                        tint = Color.Unspecified)
                }
            }
        }
    }
}

@Composable
private fun DisplayPostField(fieldValue: String,onValueChanges:(String)-> Unit) {
    BasicTextField(value = fieldValue , onValueChange = onValueChanges, modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Black1f),
        decorationBox = {innerTextField->
            Box{
                if (fieldValue.isEmpty()){
                    UiText("ما الذي يدور في ذهنك؟", fontSize = 14.sp, color = TextGray)
                }
                innerTextField()
            }
        })
}

@Composable
private fun DisplayProfile(profile: ProfileResponseModel?) {
    if (profile!=null){
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            AsyncImage(model = profile.image , contentScale = ContentScale.Crop, contentDescription = null,
                modifier = Modifier.size(56.dp).clip(shape = CircleShape))
            UiText(profile.name?:"" , fontSize = 16.sp , color = Black1f , fontWeight = FontWeight.W700)
        }
    }
}

@Composable
private fun DisplayPostsCategories(
    categories: List<PostCategoriesDto>,
    selectedCategory: PostCategoriesDto?,
    onCategoryClicked:(PostCategoriesDto)-> Unit
) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(categories.size){index->
            val currentCategory = categories[index]
            val isSelected = selectedCategory==currentCategory
            AppButton(title = currentCategory.name?:"" , fontSize = 12.sp, shape = RoundedCornerShape(12.dp),
                backgroundColor = if (!isSelected) GrayF2 else Primary ,
                paddingValues = PaddingValues(horizontal = 10.dp, vertical = 5.dp),
                textColor = if (!isSelected) TextPrimary else Color.White, fontWeight = FontWeight.W700, onClick = {
                    onCategoryClicked(currentCategory)
                })
        }
    }
}