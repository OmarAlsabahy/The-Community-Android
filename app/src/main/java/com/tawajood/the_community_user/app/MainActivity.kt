package com.tawajood.the_community_user.app

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.tawajood.the_community_user.app.language.LocalLayout
import com.tawajood.the_community_user.app.language.LocalizationProvider
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.navigation.Auth
import com.tawajood.the_community_user.app.ui.navigation.AuthStartDestination
import com.tawajood.the_community_user.app.ui.navigation.RootNavHost
import com.tawajood.the_community_user.app.ui.shared.ForceUpdateBottomSheet
import com.tawajood.the_community_user.app.ui.theme.BELTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val langVm: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition { langVm.currentLang.value == null }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val current by langVm.currentLang.collectAsState()
            val isNewVersion by langVm.isNewVersion.collectAsState()

            current?.let { lang ->
                LocalizationProvider(lang = lang) {
                    CompositionLocalProvider(LocalLayoutDirection provides LocalLayout.current) {
                        BELTheme {
                            Scaffold(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                RootNavHost(AppRoutes.Splash, it)
                            }

                            ForceUpdateBottomSheet(isNewVersion) {
                                openAppInPlayStore()
                            }
                        }
                    }
                }
            }
        }
    }

    // TODO ADD PACKAGE NAME
    private fun Context.openAppInPlayStore(packageName: String = "") {
        try {
            val playStoreIntent =
                Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri())
            playStoreIntent.setPackage("com.android.vending")
            startActivity(playStoreIntent)
        } catch (e: ActivityNotFoundException) {
            val webIntent = Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=$packageName".toUri()
            )
            startActivity(webIntent)
        }
    }
}
