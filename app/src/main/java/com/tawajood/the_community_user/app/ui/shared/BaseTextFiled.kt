package com.tawajood.the_community_user.app.ui.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tawajood.the_community_user.app.ui.theme.BorderColor

@Composable
fun BaseTextFiled(
    modifier: Modifier = Modifier,
    label: String?=null,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    isError: Boolean,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = 1,
    errorMessage: String? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    backgroundColor: Color = Color.Transparent,
    borderColor : Color = BorderColor,
    minLines: Int=1
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (label!=null){
            UiText(
                text = label,
                color = Color.Black
            )
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                UiText(
                    text = placeholderText,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = backgroundColor,
                unfocusedContainerColor = backgroundColor,
                errorContainerColor = backgroundColor,
                cursorColor = Color.Black,
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor,
                errorBorderColor = Color.Red
            ),
            singleLine = true,
            isError = isError,
            enabled = enabled,
            readOnly = readOnly,
            maxLines = maxLines,
            minLines = minLines,
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 14.sp
            ),
            shape = RoundedCornerShape(12.dp),
            supportingText = {
                AnimatedVisibility(isError && errorMessage != null) {
                    UiText(
                        text = errorMessage.orEmpty(),
                        color = Color.Red,
                        fontSize = 14.sp
                    )
                }
            },
            prefix = prefix,
            suffix = suffix,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions
        )
    }
}