package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.theme.BorderColor
import com.vanniktech.locale.Country

@Composable
fun PhoneField(
    country: Country,
    value: String,
    onValueChanged:(String)-> Unit,
    onCountryCodePressed:()-> Unit,
    placeholder: String = "5XXXXXXXX",
    isEnabled: Boolean = true
){
    Box(modifier = Modifier.fillMaxWidth().background(color = Color.Transparent, shape = RoundedCornerShape(12.dp))
        .border(width = 1.dp, color = BorderColor, shape = RoundedCornerShape(12.dp))
        .padding(horizontal = 16.dp, vertical = 12.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(modifier = Modifier.clickable{
                onCountryCodePressed()
            },verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                UiText(country.emoji)
                UiText(country.callingCodes.firstOrNull().orEmpty() , fontSize = 14.sp,
                    color = Color.Black
                )
                Icon(painter = painterResource(R.drawable.bottom_arrow_ic), contentDescription = "",
                    tint = Color.Unspecified)
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChanged,
                modifier = Modifier.weight(1f),
                singleLine = true,
                enabled = isEnabled,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                decorationBox = { innerTextField ->
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty() && placeholder.isNotEmpty()) {
                            UiText(text = placeholder, color = Color.Gray, fontSize = 14.sp)
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}