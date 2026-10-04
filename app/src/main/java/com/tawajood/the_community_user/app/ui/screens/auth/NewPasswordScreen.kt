package com.tawajood.the_community_user.app.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.language.LocalStrings
import com.tawajood.the_community_user.app.language.Strings
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.shared.AppButton
import com.tawajood.the_community_user.app.ui.shared.BaseTextFiled
import com.tawajood.the_community_user.app.ui.shared.CustomScaffold
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun NewPasswordScreen(viewModel: NewPasswordViewModel = hiltViewModel(),resetToken: String,
                       phone: String,nav:(AppRoutes)-> Unit){
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val strings = LocalStrings.current
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is NewPasswordEffect.ShowToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
                is NewPasswordEffect.Nav->{
                    nav(effect.route)
                }
            }
        }
    }
    CustomScaffold(topBarTitle = strings.newPassword,hasTopBar = true, content = {paddingValues->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(start = 16.dp, end = 16.dp, top = 40.dp)
                .fillMaxSize().padding(paddingValues).verticalScroll(scrollState),state,
                strings,
                onPasswordChanges = {
                    viewModel.sendIntent(NewPasswordIntent.OnPasswordChanges(it))
                }, onConfirmPasswordChanges = {
                    viewModel.sendIntent(NewPasswordIntent.OnConfirmPasswordChanges(it))
                }, onChangePasswordVisibility = {
                    viewModel.sendIntent(NewPasswordIntent.ChangePasswordVisibility)
                }, onChangeConfirmPasswordVisibility = {
                    viewModel.sendIntent(NewPasswordIntent.ChangeConfirmPasswordVisibility)
                }, onSubmitPressed = {
                    viewModel.sendIntent(NewPasswordIntent.OnSubmitPressed(phone,resetToken))
                })
            LoadingScreen(state.isLoading)
        }
    })
}
@Composable
fun DisplayContent(modifier: Modifier,state: NewPasswordUiState,strings: Strings,onPasswordChanges:(String)-> Unit,
                   onConfirmPasswordChanges:(String)-> Unit,onChangePasswordVisibility:()-> Unit,
                   onChangeConfirmPasswordVisibility:()-> Unit,onSubmitPressed:()-> Unit){
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painterResource(R.drawable.reset_password_ic),contentDescription = null,
            tint = Color.Unspecified)
        UiText(strings.createNewPassword, fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.W700,
            textAlign = TextAlign.Center)
        UiText(strings.createNewPasswordForYou, fontSize = 16.sp, color = TextGray, textAlign = TextAlign.Center)
        BaseTextFiled(label = strings.password, value = state.passwordFieldValue , onValueChange = onPasswordChanges,
            placeholderText = "********", isError = false, suffix = {
                Icon(painterResource(if (state.isConfirmPasswordVisible) R.drawable.eye_ic else R.drawable.eye_off_ic),contentDescription = null,
                    tint = Color.Unspecified, modifier = Modifier.clickable{
                        onChangePasswordVisibility()
                    })
            }, visualTransformation = if (state.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None)
        BaseTextFiled(label = strings.confirmPassword, value = state.confirmPasswordFieldValue , onValueChange = onConfirmPasswordChanges,
            placeholderText = "********", isError = false, suffix = {
                Icon(painterResource(if (state.isConfirmPasswordVisible) R.drawable.eye_ic else R.drawable.eye_off_ic),contentDescription = null,
                    tint = Color.Unspecified, modifier = Modifier.clickable{
                        onChangeConfirmPasswordVisibility()
                    })
            }, visualTransformation = if (state.isConfirmPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None)
        AppButton(title = strings.save, modifier = Modifier.padding(top = 96.dp).fillMaxWidth(), shape = RoundedCornerShape(16.dp),
            backgroundColor = Primary , paddingValues = PaddingValues(vertical = 12.dp),
            textColor = Color.White, isEnabled = state.isButtonEnabled, onClick = onSubmitPressed
        )
    }
}