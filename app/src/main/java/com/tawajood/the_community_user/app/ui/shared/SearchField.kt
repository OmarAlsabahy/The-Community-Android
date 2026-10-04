package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.theme.BorderColor

@Composable
fun SearchField(value: String, onValueChanges:(String)-> Unit, modifier: Modifier = Modifier.fillMaxWidth(),
                onSearchPressed:()-> Unit){
    Box(modifier = modifier.background(color = Color.Transparent , shape = RoundedCornerShape(12.dec()))
        .border(width = 1.dp, shape = RoundedCornerShape(12.dp), color = BorderColor)){
        Row(modifier = Modifier.padding(vertical = 11.dp, horizontal = 12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(painterResource(R.drawable.search_ic) , contentDescription = null,
                tint = Color.Unspecified)
            BasicTextField(value = value, onValueChange = onValueChanges, modifier = Modifier.weight(1f),
                textStyle = TextStyle(color =  Color.Black,
                    fontWeight =  FontWeight.W400),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch ={
                    onSearchPressed()
                }), decorationBox = {innerTextField->
                    if (value.isEmpty()){
                        UiText("بحث...", color = Color(0xFFAEADB2))
                    }
                    innerTextField()
                })
        }
    }

}