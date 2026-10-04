package com.tawajood.the_community_user.app.ui.screens.guide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.screens.home.TopHeaderButton
import com.tawajood.the_community_user.app.ui.shared.AppButton
import com.tawajood.the_community_user.app.ui.shared.GuideCard
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.app.ui.shared.SearchField
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.app.ui.theme.GrayF2
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.TextPrimary
import com.tawajood.the_community_user.domain.models.guide.GuideCategoryResponseDto

@Composable
fun GuideScreen(viewModel: GuideViewModel = hiltViewModel()){
    val strings = LocalStrings.current
    val state by viewModel.state.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.White, topBar = {
        Box(modifier = Modifier.fillMaxWidth().statusBarsPadding()){
            IconButton(onClick = {}) {
                Icon(painterResource(R.drawable.back_ic), contentDescription = null,
                    tint = Color.Unspecified, modifier = Modifier.align(Alignment.CenterStart))
            }
            UiText(strings.applicationGuide, fontSize = 16.sp, color = Black1f, fontWeight = FontWeight.W700,
                textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.Center))
            TopHeaderButton(icon = R.drawable.notification_ic, modifier = Modifier.align(Alignment.CenterEnd)) { }
        }
    }) {innerPadding->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp).fillMaxSize().padding(innerPadding),
                state, onValueChanges = {value->
                    viewModel.sendIntent(GuideIntent.OnSearchValueChanges(value))
                }, onCategoryClicked = {category->
                    viewModel.sendIntent(GuideIntent.OnCategoryClicked(category))
                }, onSearchPressed = {
                    viewModel.sendIntent(GuideIntent.OnSearch)
                })
            LoadingScreen(state.isLoading)
        }
    }
}

@Composable
private fun DisplayContent(modifier: Modifier, state: GuideUiState,onValueChanges:(String)-> Unit,
                           onCategoryClicked: (GuideCategoryResponseDto) -> Unit,onSearchPressed:()-> Unit) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(24.dp)) {
        item{
            SearchField(value = state.searchValue,onValueChanges, onSearchPressed = onSearchPressed)
        }
        item{
            DisplayCategories(state.categories,state.selectedCategory,onCategoryClicked)
        }
        items(state.guides.size){index->
            val currentGuide = state.guides[index]
            GuideCard(currentGuide)
        }
    }
}

@Composable
private fun DisplayCategories(categories: List<GuideCategoryResponseDto>, selectedCategory: GuideCategoryResponseDto?,
                              onCategoryClicked:(GuideCategoryResponseDto)-> Unit) {
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