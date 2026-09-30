package com.tawajood.the_community_user.app.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.shared.AppButton
import com.tawajood.the_community_user.app.ui.shared.CustomScaffold
import com.tawajood.the_community_user.app.ui.shared.LoadingScreen
import com.tawajood.the_community_user.app.ui.shared.PhoneField
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun ForgetPasswordScreen(viewModel: ForgetPasswordViewModel = hiltViewModel(),
                         nav:(AppRoutes)-> Unit){
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ForgetPasswordEffect.ShowErrorToast -> {
                    ToastUtils.showErrorToast(context, effect.message)
                }
                is ForgetPasswordEffect.Nav->{
                    nav(effect.route)
                }
            }
        }
    }
    CustomScaffold(topBarTitle = "نسيت كلمة المرور",hasTopBar = true, content = {paddingValue->
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.padding(horizontal = 16.dp).fillMaxSize().padding(paddingValue)
                .verticalScroll(scrollState), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Icon(painterResource(R.drawable.reset_password_ic),contentDescription = null,
                    tint = Color.Unspecified)
                UiText("تعيين كلمة المرور", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center, modifier = Modifier.padding(top = 32.dp))
                UiText("يرجى إدخال رقمك لمتابعة تغيير كلمة المرور", fontSize = 18.sp,
                    textAlign = TextAlign.Center, color = TextGray, modifier = Modifier.padding(top = 12.dp,
                        bottom = 40.dp)
                )
                PhoneField(country = state.selectedCountry , state.phoneFieldValue,
                    onValueChanged = {value->
                        viewModel.sendIntent(ForgetPasswordIntent.OnPhoneChanges(value))
                    }, onCountryCodePressed = {})
                AppButton(modifier = Modifier.padding(top = 96.dp).fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                    title = "التالي", fontSize = 16.sp, textColor = Color.White, isEnabled = state.isButtonEnabled, onClick = {
                        viewModel.sendIntent(ForgetPasswordIntent.ForgetPassword)
                    })
            }
            LoadingScreen(state.isLoading)
        }
    })
}