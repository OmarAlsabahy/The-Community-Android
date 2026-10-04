package com.tawajood.the_community_user.app.ui.shared

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.theme.Black1f
import com.tawajood.the_community_user.app.ui.theme.BorderColor
import com.tawajood.the_community_user.app.ui.theme.GrayF2
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.Primary40
import com.tawajood.the_community_user.app.ui.theme.Primary50
import com.tawajood.the_community_user.domain.models.guide.GuideResponseDto

@Composable
fun GuideCard(guide: GuideResponseDto){
    val context = LocalContext.current
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(width = 1.dp, color = BorderColor)) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DisplayGuideMainIcon(R.drawable.market_ic)
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    UiText(guide.title?:"", fontSize = 16.sp, color = Black1f, fontWeight = FontWeight.W700)
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Filled.AccessTimeFilled , contentDescription = null,
                            tint = Primary, modifier = Modifier.size(20.dp))
                        UiText("9 AM : 10 PM", fontSize = 12.sp, color = Primary40)
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                DisplayGuideIcon(R.drawable.location_filled_ic, onClick = {
                    val mapIntent = Intent(Intent.ACTION_VIEW , Uri.parse("geo:${guide.location.lat}?q=${guide.location.lat},${guide.location.long}"))
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                })
                DisplayGuideIcon(R.drawable.phone_filled_ic,{
                    val intent = Intent(Intent.ACTION_DIAL,Uri.parse("tel:${guide.phone}"))
                    context.startActivity(intent)
                })
            }
        }
    }
}

@Composable
private fun DisplayGuideIcon(icon: Int,onClick:()-> Unit) {
    Card(onClick = onClick,shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(GrayF2)) {
        Icon(painterResource(icon),contentDescription = null, modifier = Modifier.padding(11.dp),
            tint = Primary50
        )
    }
}

@Composable
private fun DisplayGuideMainIcon(icon: Int) {
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(GrayF2)) {
        Icon(painterResource(icon),contentDescription = null, tint = Primary,
            modifier = Modifier.padding(12.dp))
    }
}