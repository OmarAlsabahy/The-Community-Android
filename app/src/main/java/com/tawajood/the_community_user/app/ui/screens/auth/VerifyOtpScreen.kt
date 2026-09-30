package com.tawajood.the_community_user.app.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.tawajood.the_community_user.app.ui.shared.OtpTextField
import com.tawajood.the_community_user.app.ui.shared.UiText
import com.tawajood.the_community_user.app.ui.theme.Secondary
import com.tawajood.the_community_user.app.ui.theme.TextGray
import com.tawajood.the_community_user.utils.ToastUtils

@Composable
fun VerifyOtpScreen(viewModel: VerifyOtpViewModel = hiltViewModel(),
                    phoneNumber: String,countryCode: String,nav:(AppRoutes)-> Unit){
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect){
                is VerifyOtpEffect.ShowToast->{
                    ToastUtils.showErrorToast(context,effect.message)
                }
                is VerifyOtpEffect.Nav->{
                    nav(effect.route)
                }
            }
        }
    }
    CustomScaffold(hasTopBar = true, topBarTitle = "تاكيد  الحساب", content = {innerPadding->
        Box(modifier = Modifier.fillMaxSize()){
            DisplayContent(Modifier.padding(top = 40.dp, start = 16.dp, end = 16.dp).fillMaxSize()
                .padding(innerPadding).verticalScroll(scrollState),state,
                phoneNumber,countryCode, onOtpChanges = {value->
                    viewModel.sendIntent(VerifyOtpIntent.OnOtpChanges(value))
                }, onRestartTimer = {
                    viewModel.sendIntent(VerifyOtpIntent.RecreateTimer)
                }, onSubmitPressed = {
                    viewModel.sendIntent(VerifyOtpIntent.OnSubmitPressed(phoneNumber))
                })
            LoadingScreen(state.isLoading)
        }
    })
}
@Composable
private fun DisplayContent(modifier: Modifier,state: VerifyOtpUiState,phoneNumber: String ,
                           countryCode: String,onOtpChanges:(String)-> Unit,onRestartTimer:()-> Unit,
                           onSubmitPressed:()-> Unit){
    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painterResource(R.drawable.verifyotp_ic),contentDescription = null, tint = Color.Unspecified,
            modifier = Modifier.padding(bottom = 32.dp))
        UiText("التحقق من الهاتف", fontSize = 24.sp, color = Color.Black, fontWeight = FontWeight.W700,
            textAlign = TextAlign.Center)
        UiText("أدخل الرقم المكون من 6 أرقام الذي أرسلناه عبر رقم الهاتف: ${phoneNumber}",
            fontSize = 16.sp, color = TextGray, modifier = Modifier.padding(top = 12.dp),
            textAlign = TextAlign.Center)
        OtpTextField(modifier = Modifier.padding(top = 40.dp).fillMaxWidth(),
            otpValue = state.otpValue, digitsCount = 5, onValueChange = onOtpChanges)
        UiText("${state.timer} ثانية", fontSize = 12.sp, color = Secondary, fontWeight = FontWeight.W700,
            modifier = Modifier.padding(top = 32.dp))
        if (state.timer==0){
            Row(modifier = Modifier.padding(top = 24.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                UiText("لم تستلم الرمز؟", fontSize = 16.sp, color = TextGray, fontWeight = FontWeight.W400)
                UiText(" اعادة ارسال", fontSize = 16.sp, color = Secondary, fontWeight = FontWeight.W400,
                    modifier = Modifier.clickable{
                        onRestartTimer()
                    })
            }
        }
        AppButton(title = "التالي", modifier = Modifier.padding(top = 96.dp).fillMaxWidth(), onClick = onSubmitPressed,
            isEnabled = state.isButtonEnabled)
    }
}