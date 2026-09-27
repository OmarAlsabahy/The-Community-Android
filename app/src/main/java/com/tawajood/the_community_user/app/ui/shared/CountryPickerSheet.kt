package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eygraber.compose.country.code.picker.CountryCodePicker
import com.tawajood.the_community_user.app.language.LocalStrings
import com.vanniktech.locale.Country

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerSheet(
    visible: Boolean,
    initial: Country,
    onDismiss: () -> Unit,
    onSelect: (Country) -> Unit,
) {
    if (!visible) return

    val strings = LocalStrings.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(16.dp)
        ) {
            UiText(strings.selectCountry, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))

            CountryCodePicker(
                initialCountry = initial,
                onClick = {
                    onSelect(it)
                    onDismiss()
                },
                searchField = { textFieldState ->
                    CountryPickerSearchField(
                        textFieldState = textFieldState,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                countryItemLeadingContent = { Text(it.emoji) },
                countryItemHeadlineContent = {
                    UiText("${it.displayName()} (${it.callingCodes.first()})")
                },
                countryItemColors = ListItemDefaults.colors(
                    containerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryPickerSearchField(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor = if (isFocused) Color.Black else Color(0xFFE5E7EB)
    val bgColor = Color.White

    val strings = LocalStrings.current

    Column(modifier) {
        UiText(text = strings.searchCountry, fontSize = 12.sp, color = Color.Gray)
        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(bgColor)
                .border(1.dp, borderColor, RoundedCornerShape(14.dp))
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )
            Spacer(Modifier.width(8.dp))

            BasicTextField(
                state = textFieldState,
                modifier = Modifier.weight(1f),
                interactionSource = interactionSource,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text
                ),
                decorator = { inner ->
                    if (textFieldState.text.isEmpty()) {
                        UiText(strings.searchByNameOrCode, color = Color.Gray, fontSize = 14.sp)
                    }
                    inner()
                }
            )
        }
    }
}
