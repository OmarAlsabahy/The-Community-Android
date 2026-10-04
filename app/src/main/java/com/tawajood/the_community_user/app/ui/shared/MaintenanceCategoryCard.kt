package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tawajood.the_community_user.app.ui.theme.GrayF5
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.domain.models.customerServices.MaintenanceCategoryDto

@Composable
fun MaintenanceCategoryCard(category: MaintenanceCategoryDto,isSelected: Boolean,
                            onCardClicked: (MaintenanceCategoryDto) -> Unit){
    Column(modifier = Modifier.clickable{
        onCardClicked(category)
    },verticalArrangement = Arrangement.spacedBy(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Card(shape = CircleShape, colors = CardDefaults.cardColors(if (!isSelected) GrayF5 else Primary)) {
            AsyncImage(model = category.image,contentDescription = null, contentScale = ContentScale.Fit,
                modifier = Modifier.padding(16.dp).size(24.dp))
        }
        UiText(category.name?:"", fontSize = 12.sp, color = TextGray, textAlign = TextAlign.Center)
    }
}