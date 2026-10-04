package com.tawajood.the_community_user.app.ui.screens.customerServices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.app.language.Strings
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.screens.home.TopHeaderButton
import com.tawajood.the_community_user.app.ui.shared.CustomerServiceCategoryCard
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun CustomerServicesScreen(viewModel: CustomerServicesViewModel = hiltViewModel(),
                           nav:(AppRoutes)-> Unit){
    val strings = LocalStrings.current
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is CustomerServicesEffect.ShowToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
                is CustomerServicesEffect.Nav -> {
                    nav(effect.route)
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.White, topBar = {
        Box(modifier = Modifier.fillMaxWidth().statusBarsPadding()){
            IconButton(onClick = {}) {
                Icon(painterResource(R.drawable.back_ic), contentDescription = null,
                    tint = Color.Unspecified, modifier = Modifier.align(Alignment.CenterStart))
            }
            UiText(strings.customerServices, fontSize = 16.sp, color = Black1f, fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
            TopHeaderButton(icon = R.drawable.notification_ic, modifier = Modifier.align(Alignment.CenterEnd)) { }
        }
    }) {innerPadding->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(horizontal = 16.dp).fillMaxSize().padding(innerPadding),state,
                strings, onCategoryClicked = {
                    viewModel.sendIntent(CustomerServicesIntent.OnCategoryClicked(it))
                })
        }
    }
}

@Composable
private fun DisplayContent(modifier: Modifier, state: CustomerServicesUiState,strings: Strings,
                           onCategoryClicked:(Int?)-> Unit) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            UiText(strings.howCanWeHelpYou, fontSize = 18.sp, color = Primary, fontWeight = FontWeight.W700,
                modifier = Modifier.padding(bottom = 8.dp))
        }
        items(count = state.categories.size){index->
            CustomerServiceCategoryCard(state.categories[index], onCardClicked = onCategoryClicked)
        }
    }
}