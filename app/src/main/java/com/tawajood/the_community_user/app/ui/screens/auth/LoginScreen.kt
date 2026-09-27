package com.tawajood.the_community_user.app.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.shared.AppButton
import com.tawajood.the_community_user.app.ui.shared.BaseTextFiled
import com.tawajood.the_community_user.app.ui.shared.CountryPickerSheet
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.app.ui.shared.PhoneField
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Primary
import com.tawajood.the_community_user.app.ui.theme.Secondary
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.app.ui.theme.TextPrimary
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(),nav:(AppRoutes)-> Unit){
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is LoginEffect.ShowErrorToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
                is LoginEffect.Nav->{
                    nav(effect.route)
                }
            }
        }
    }
    if(state.showCountryDialog){
        CountryPickerSheet(visible = true, onDismiss = {
            viewModel.sendIntent(LoginIntent.OnCountryDialogDismissed)
        }, initial = state.selectedCountry) { country->
            viewModel.sendIntent(LoginIntent.OnCountrySelected(country))
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.White) {innerPadding->
        Box(Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(horizontal = 16.dp).fillMaxSize().padding(innerPadding)
                .verticalScroll(scrollState),
                state, onPhoneChanges = {value->
                    viewModel.sendIntent(LoginIntent.OnPhoneChanges(value))
                }, onPasswordChanges = {value->
                    viewModel.sendIntent(LoginIntent.OnPasswordChanges(value))
                }, onCountryPressed = {
                    viewModel.sendIntent(LoginIntent.OnCountryPressed)
                }, onChanePasswordVisibility = {
                    viewModel.sendIntent(LoginIntent.OnChangePasswordVisibility)
                }, onSubmitPressed = {
                    viewModel.sendIntent(LoginIntent.OnSubmitPressed)
                })
            LoadingScreen(state.isLoading)
        }
    }
}

@Composable
private fun DisplayContent(modifier: Modifier, state: LoginUiState,
                           onPhoneChanges: (String) -> Unit,onPasswordChanges: (String) -> Unit,
                           onCountryPressed: () -> Unit,
                           onChanePasswordVisibility:()-> Unit,
                           onSubmitPressed: () -> Unit) {
    val strings = LocalStrings.current
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.logo), contentDescription = null,
            modifier = Modifier.size(104.dp), contentScale = ContentScale.Fit)
        UiText(strings.login, fontSize = 24.sp, color = TextPrimary , fontWeight = FontWeight.W700,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp), textAlign = TextAlign.Center)
        UiText(strings.welcomeBack, fontSize = 14.sp , color = TextGray, textAlign = TextAlign.Center)
        DisplayForm(Modifier.fillMaxWidth(),state, onPhoneChanges = onPhoneChanges,
            onPasswordChanges = onPasswordChanges, onCountryPressed = onCountryPressed,
            onChangePasswordVisibility = onChanePasswordVisibility)
//        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
//            TextButton(onClick = {}) {
//                UiText(strings.forgetPasswordQuestion, fontSize = 14.sp, fontWeight = FontWeight.W700,
//                    color = Primary50)
//            }
//        }
        DisplayButtons(Modifier.padding(top = 56.dp).fillMaxWidth(),
            state.isSubmitButtonEnabled,onSubmitPressed,{})
    }
}

@Composable
private fun DisplayButtons(
    modifier: Modifier,
    isButtonEnabled: Boolean,
    onSubmitPressed: () -> Unit,
    onUserCyclePressed: () -> Unit
) {
    val strings = LocalStrings.current
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        AppButton(modifier = Modifier.fillMaxWidth(), backgroundColor = Primary , textColor = Color.White,
            title = strings.login, fontSize = 16.sp, shape = RoundedCornerShape(16.dp),
            onClick = onSubmitPressed,isEnabled = isButtonEnabled)
        TextButton(onClick = onUserCyclePressed) {
            UiText(strings.loginAsAGuest, fontSize = 14.sp, color = Secondary, fontWeight = FontWeight.W700)
        }
    }
}

@Composable
private fun DisplayForm(modifier: Modifier, state: LoginUiState,
                        onPhoneChanges:(String)-> Unit,onCountryPressed:()-> Unit,
                        onPasswordChanges:(String)-> Unit,onChangePasswordVisibility:()-> Unit) {
    val strings = LocalStrings.current
    Column(modifier, verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            UiText(strings.phoneNumber)
            PhoneField(country = state.selectedCountry , value = state.phoneValue, onValueChanged = onPhoneChanges,
                onCountryCodePressed = onCountryPressed)
        }
        BaseTextFiled(label = strings.password, value = state.passwordValue , onValueChange = onPasswordChanges,
            placeholderText = "********", isError = false, suffix = {
                Icon(painterResource(if (state.isPasswordSecured) R.drawable.eye_ic else R.drawable.eye_off_ic),contentDescription = null,
                    tint = Color.Unspecified, modifier = Modifier.clickable{
                        onChangePasswordVisibility()
                    })
            }, visualTransformation = if (state.isPasswordSecured) PasswordVisualTransformation() else VisualTransformation.None)
    }
}