package com.tawajood.the_community_user.app.ui.screens.customerServices

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.app.ui.screens.home.TopHeaderButton
import com.tawajood.the_community_user.app.ui.shared.MaintenanceCategoryCard
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.domain.models.customerServices.MaintenanceCategoryDto

@Composable
fun MaintenanceScreen(viewModel: MaintenanceViewModel = hiltViewModel()){
    val strings = LocalStrings.current
    val state by viewModel.state.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize() , containerColor = Color.White, topBar = {
        Box(modifier = Modifier.fillMaxWidth()){
            IconButton(onClick = {}) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Black1f
                    , modifier = Modifier.align(Alignment.CenterStart))
            }
            UiText("الصيانة", fontSize = 16.sp, fontWeight = FontWeight.W700, color = Black1f,
                textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
            TopHeaderButton(icon = R.drawable.notification_ic) { }
        }
    }) {innerPadding->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(horizontal = 16.dp).fillMaxSize().padding(innerPadding),
                state, onCategoryClicked = {category->
                    viewModel.sendIntent(MaintenanceIntent.OnCategorySelected(category))
                })
        }
    }
}
@Composable
fun DisplayContent(modifier: Modifier,state: MaintenanceUiState,onCategoryClicked:(MaintenanceCategoryDto)-> Unit){
    LazyColumn(modifier = modifier) {
        item {
            DisplayCategories(Modifier.fillMaxWidth(),state,onCategoryClicked)
        }
    }
}

@Composable
private fun DisplayCategories(modifier: Modifier, state: MaintenanceUiState,
                              onCardClick: (MaintenanceCategoryDto) -> Unit) {
    LazyRow(modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(state.categories.size){index->
            val currentCategory = state.categories[index]
            MaintenanceCategoryCard(currentCategory,currentCategory==state.selectedCategory,
                onCardClicked = onCardClick)
        }
    }
}