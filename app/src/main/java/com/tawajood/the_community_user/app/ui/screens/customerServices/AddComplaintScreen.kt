package com.tawajood.the_community_user.app.ui.screens.customerServices

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
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.VideoFrameDecoder
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.shared.AppButton
import com.tawajood.the_community_user.app.ui.shared.BaseTextFiled
import com.tawajood.the_community_user.app.ui.shared.CustomScaffold
import com.tawajood.the_community_user.app.ui.shared.PhoneField
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.GrayFa
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.TextGray
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.text.font.FontWeight
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.utils.ToastUtils
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.app.language.Strings

@Composable
fun AddComplaintScreen(viewModel: AddComplaintViewModel = hiltViewModel(),categoryId: Int,returnToHome:(AppRoutes)-> Unit){
    val strings = LocalStrings.current
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia()) {uris->
        viewModel.sendIntent(AddComplaintIntent.AddMedia(uris))
    }
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is AddComplaintEffect.SelectMedia->{
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }
                is AddComplaintEffect.ShowToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
                is AddComplaintEffect.ReturnToHome->{
                    returnToHome(effect.route)
                }
            }
        }
    }

    if (state.showSuccessDialog){
        DisplaySuccessDialog(strings = strings, returnToHome = {
            viewModel.sendIntent(AddComplaintIntent.ReturnToHome)
        })
    }
    CustomScaffold(topBarTitle = strings.submitComplaint,hasTopBar = true, bottomAppBar = {
        AppButton(modifier = Modifier.padding(16.dp).fillMaxWidth(), backgroundColor = Primary , textColor = Color.White,
            title = strings.publish, paddingValues = PaddingValues(12.dp), onClick = {
                viewModel.sendIntent(AddComplaintIntent.OnSubmit(categoryId))
            })
    }, content = {paddingValues->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(horizontal = 16.dp).fillMaxSize().padding(paddingValues)
                .verticalScroll(scrollState), state,context,strings,
                onPhoneValueChanges = {value->
                    viewModel.sendIntent(AddComplaintIntent.OnPhoneValueChanges(value))
                }, onAddressValueChanges = {value->
                    viewModel.sendIntent(AddComplaintIntent.OnAddressValueChanges(value))
                }, onComplaintValueChanges = {value->
                    viewModel.sendIntent(AddComplaintIntent.OnComplaintValueChanges(value))
                }, onAddMediaClicked = {
                    viewModel.sendIntent(AddComplaintIntent.OnSelectMediaPressed)
                })
            LoadingScreen(state.isLoading)
        }
    })
}

@Composable
private fun DisplaySuccessDialog(strings: Strings, returnToHome: () -> Unit) {
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    modifier = Modifier.clickable { returnToHome() }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                painter = painterResource(id = R.drawable.success_icon),
                contentDescription = null,
                modifier = Modifier.size(100.dp),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.height(24.dp))
            UiText(
                text = strings.complaintSubmittedSuccessfully,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            UiText(
                text = strings.yourProblemWillBeSolvedSoon,
                fontSize = 14.sp,
                color = TextGray
            )
            Spacer(modifier = Modifier.height(32.dp))
            AppButton(
                title = strings.returnToHomePage,
                backgroundColor = Color.White,
                textColor = Primary,
                strokeWidth = 1.dp,
                strokeColor = Primary,
                modifier = Modifier.fillMaxWidth(),
                onClick = returnToHome,
                shape = RoundedCornerShape(24.dp)
            )
        }
    }
}

@Composable
private fun DisplayContent(modifier: Modifier,state: AddComplaintUiState,context: Context,
                           strings: Strings,
                           onPhoneValueChanges:(String)-> Unit,onAddressValueChanges:(String)-> Unit,
                           onComplaintValueChanges:(String)-> Unit,
                           onAddMediaClicked:()-> Unit){
    Column(modifier, verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            UiText(strings.phoneNumber)
            PhoneField(country = state.selectedCountry , value = state.phoneFieldValue, onValueChanged =onPhoneValueChanges,
                onCountryCodePressed = {})
        }
        BaseTextFiled(value = state.addressFieldValue , onValueChange = onAddressValueChanges, isError = false,
            placeholderText = strings.address, label = strings.address)
        BaseTextFiled(value = state.complaintFieldValue , onValueChange = onComplaintValueChanges, isError = false,
            placeholderText = strings.complaintPlaceholder,
            maxLines = 5, minLines = 3, label = strings.complaint)
        AddImageBox(Modifier.fillMaxWidth(), strings = strings, onAddClicked = onAddMediaClicked)
        DisplaySelectedMedia(Modifier.fillMaxWidth(),state.selectedImages,context)
    }
}

@Composable
private fun DisplaySelectedMedia(modifier: Modifier, media: List<Uri>, context: Context) {
    if (media.isNotEmpty()){
        val imageLoader = remember {
            ImageLoader.Builder(context = context)
                .components { add(VideoFrameDecoder.Factory()) }
                .build()

        }
        FlowRow(modifier = modifier, verticalArrangement = Arrangement.Center ,
            horizontalArrangement = Arrangement.spacedBy(8.dp), maxItemsInEachRow = 3) {
            media.forEach { media ->
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(media)
                        .videoFrameMillis(1000)
                        .crossfade(true)
                        .build(),
                    imageLoader = imageLoader,
                    contentDescription = null,
                    modifier = Modifier.weight(1f).aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(8.dp))
                )
            }
        }
    }
}

@Composable
private fun AddImageBox(modifier: Modifier,strings: Strings, onAddClicked:()-> Unit) {
    Column(modifier.clickable(enabled = true, onClick = onAddClicked), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        UiText(strings.addImageOrVideo)
        Box(Modifier.fillMaxWidth().aspectRatio(361f/96f).background(color = GrayFa, shape = RoundedCornerShape(13.dp))
            .drawBehind{
                drawRoundRect(
                    color = Color(0xFFE4E7E9),
                    style = Stroke(
                        width = 1.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(8.dp.toPx(),8.dp.toPx()))
                    ),
                    cornerRadius = CornerRadius(13.dp.toPx())
                )
            }){
            Column(modifier = Modifier.matchParentSize(), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                Icon(painter = painterResource(R.drawable.add_image_ic),
                    contentDescription = null, modifier = Modifier.padding(bottom = 8.dp).size(20.dp), tint = Color.Unspecified)
                UiText(strings.addImageOrVideo, fontSize = 12.sp, color = Primary, textAlign = TextAlign.Center)
            }
        }
    }
}