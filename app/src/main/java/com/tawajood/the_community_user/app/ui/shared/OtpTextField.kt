package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.app.ui.theme.BorderColor
import com.tawajood.the_community_user.app.ui.theme.Primary

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpValue: String = "",
    digitsCount: Int = 4,
    autoFocus: Boolean = false,
    onValueChange: (otp: String) -> Unit = { },
    onOtpComplete: (otp: String) -> Unit = { },
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(value = false) }

    if (autoFocus) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }

    BasicTextField(
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        value = otpValue,
        onValueChange = { newValue ->
            if (newValue.length <= digitsCount && newValue.all { it.isDigit() }) {
                onValueChange(newValue)
                if (newValue.length == digitsCount) {
                    onOtpComplete(newValue)
                    focusManager.clearFocus()
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
            ) {
                repeat(digitsCount) { index ->
                    val char = otpValue.getOrNull(index)?.toString() ?: ""
                    val isCellFocused = isFocused && (index == otpValue.length || (index == digitsCount - 1 && otpValue.length == digitsCount))

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .aspectRatio(50f / 48f)
                            .background(
                                color =  Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = if (isCellFocused) 2.dp else 1.dp,
                                color = if (isCellFocused) Primary else BorderColor,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        UiText(
                            modifier = Modifier.align(Alignment.Center),
                            text = char,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    if (index < digitsCount - 1) {
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }
        }
    )
}


