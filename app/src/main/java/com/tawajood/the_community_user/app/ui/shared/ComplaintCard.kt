package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.app.ui.theme.BorderColor
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.domain.models.customerServices.ComplaintResponseDto

@Composable
fun ComplaintCard(complaint: ComplaintResponseDto){
    val strings = LocalStrings.current
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(Color.White)
        , border = BorderStroke(width = 1.dp, color = BorderColor)) {
        Column(modifier = Modifier.padding(12.dp).fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(Modifier.fillMaxWidth()) {
                DisplayDescItem(R.drawable.phone_fille_ic, strings.phoneNumber, complaint.phone,
                    Modifier.weight(1f))
                DisplayDescItem(R.drawable.location_fill_ic, strings.address, complaint.address,
                    Modifier.weight(1f))
            }
            UiText(strings.complaint, fontSize = 14.sp, color = Primary, fontWeight = FontWeight.W700,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp))
            UiText(complaint.description?:"", fontSize = 12.sp, color = TextGray)
        }
    }
}

@Composable
private fun DisplayDescItem(icon: Int, title: String, value: String?, modifier: Modifier) {
    Row(modifier,verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Card(colors = CardDefaults.cardColors(Color(0xFFe4e5eb)),
            shape = CircleShape) {
            Icon(painter = painterResource(icon) , contentDescription = null , modifier = Modifier.padding(6.dp),
                tint = Primary
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            UiText(title , fontSize = 14.sp, color = Primary, fontWeight = FontWeight.W700,
                overflow = TextOverflow.Ellipsis, maxLines = 1)
            UiText(value?:"", fontSize = 12.sp, color = TextGray, overflow = TextOverflow.Ellipsis,
                maxLines = 1)
        }
    }
}