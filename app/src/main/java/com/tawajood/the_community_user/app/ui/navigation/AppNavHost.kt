package com.tawajood.the_community_user.app.ui.navigation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tawajood.the_community_user.app.ui.screens.auth.LoginScreen
import com.tawajood.the_community_user.app.ui.screens.auth.VerifyOtpScreen
import com.tawajood.the_community_user.app.ui.screens.home.HomeScreen
import com.tawajood.the_community_user.app.ui.screens.splash.SplashScreen

@Composable
fun RootNavHost(
    startDestination: Any,
    paddingValues: PaddingValues
) {
    val rootController = rememberNavController()
    CompositionLocalProvider(LocalNavigationProvider provides rootController) {
        NavHost(
            navController = rootController,
            startDestination = startDestination,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable<AppRoutes.Splash> {
                SplashScreen(
                    navToOnBoarding = {},
                    navToAuth = {
                        rootController.navigate(AppRoutes.Login) {
                            popUpTo(0)
                        }
                    },
                    navToHome = {
                        rootController.navigate(AppRoutes.Home){
                            popUpTo(0)
                        }
                    }
                )
            }
            composable<AppRoutes.Login> {
                LoginScreen() { route ->
                    rootController.navigate(route) {
                    }
                }
            }
            composable<AppRoutes.VerifyOtpScreen> {
                val args = it.toRoute<AppRoutes.VerifyOtpScreen>()
                VerifyOtpScreen(countryCode = args.countryCode, phoneNumber = args.phoneNumber)
            }
            composable<AppRoutes.Home> {
                HomeScreen()
            }
        }
    }
}

//@Composable
//fun AppNavHost(
//    navToAuth: () -> Unit
//) {
//    val appNavController = rememberNavController()
//    CompositionLocalProvider(LocalNavigationProvider provides appNavController) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White),
//        ) {
//            BottomNavigation(appNavController) {
////                NavHost(
////                    navController = appNavController,
////                    startDestination = Home,
////                    modifier = Modifier
////                        .fillMaxSize()
////                ) {
////
////                }
//            }
//        }
//    }
//}
