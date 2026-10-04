package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.app.ui.theme.GrayF2
import com.tawajood.the_community_user.domain.models.customerServices.CustomerServicesCategoryResponseDto

@Composable
fun CustomerServiceCategoryCard(category: CustomerServicesCategoryResponseDto,
                                onCardClicked:(Int?)-> Unit){
    Card(onClick = {onCardClicked(category.id)},modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(GrayF2)) {
        Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Icon(painterResource(R.drawable.phone_filled_ic), contentDescription = null,
                    tint = Color.Unspecified)
                UiText(category.name?:"", fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.W700)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight , contentDescription = null,
                tint = Black1f, modifier = Modifier.size(20.dp)
            )
        }
    }
}