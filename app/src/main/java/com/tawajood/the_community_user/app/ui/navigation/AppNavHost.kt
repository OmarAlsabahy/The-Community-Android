package com.tawajood.the_community_user.app.ui.navigation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tawajood.the_community_user.app.ui.screens.auth.LoginScreen
import com.tawajood.the_community_user.app.ui.screens.auth.VerifyOtpScreen
import com.tawajood.the_community_user.app.ui.screens.main.MainScreen
import com.tawajood.the_community_user.app.ui.screens.society.PostDetailsScreen
import com.tawajood.the_community_user.app.ui.screens.society.PostImageScreen
import com.tawajood.the_community_user.app.ui.screens.splash.SplashScreen
import com.tawajood.the_community_user.domain.models.society.PostDto
import com.tawajood.the_community_user.utils.serializableNavType
import kotlin.reflect.typeOf

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
                        rootController.navigate(AppRoutes.Main){
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
            composable<AppRoutes.Main>{
                val postDto = remember(it){
                    it.savedStateHandle.getStateFlow<PostDto?>("post", null)
                }
                MainScreen(postFlow = postDto, nav = {route->
                    if (route!=null){
                        rootController.navigate(route)
                    }
                })
            }
            composable<AppRoutes.PostDetails>{
                val args = it.toRoute<AppRoutes.PostDetails>()
                PostDetailsScreen(id = args.id, pop = {
                    rootController.popBackStack()
                }, returnData = {post->
                    rootController.previousBackStackEntry?.savedStateHandle?.set<PostDto?>("post",post)
                })
            }
            composable<AppRoutes.PostImage>(
                typeMap = mapOf(
                    typeOf<PostDto>() to serializableNavType<PostDto>()
                )
            ){
                val args = it.toRoute<AppRoutes.PostImage>()
                PostImageScreen(args.post, pop = {
                    rootController.popBackStack()
                }, returnData = {post->
                    rootController.previousBackStackEntry?.savedStateHandle?.set<PostDto?>("post",post)
                }, nav = {route->
                    rootController.navigate(route)
                })
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
