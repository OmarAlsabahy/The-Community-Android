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
import com.tawajood.the_community_user.app.ui.screens.auth.ForgetPasswordScreen
import com.tawajood.the_community_user.app.ui.screens.auth.LoginScreen
import com.tawajood.the_community_user.app.ui.screens.auth.NewPasswordScreen
import com.tawajood.the_community_user.app.ui.screens.auth.VerifyOtpScreen
import com.tawajood.the_community_user.app.ui.screens.main.MainScreen
import com.tawajood.the_community_user.app.ui.screens.society.AddPostScreen
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
            composable<AppRoutes.ForgetPassword>{
                ForgetPasswordScreen(nav = {route->
                    rootController.navigate(route)
                })
            }
            composable<AppRoutes.VerifyOtpScreen> {
                val args = it.toRoute<AppRoutes.VerifyOtpScreen>()
                VerifyOtpScreen(countryCode = args.countryCode, phoneNumber = args.phoneNumber,
                    nav = {route->
                        rootController.navigate(route)
                    })
            }
            composable<AppRoutes.NewPassword>{
                val args = it.toRoute<AppRoutes.NewPassword>()
                NewPasswordScreen(resetToken = args.resetToken, phone = args.phone, nav = {route->
                    rootController.navigate(route){
                        popUpTo(0)
                    }
                })
            }
            composable<AppRoutes.Main>{
                val postDto = remember(it){
                    it.savedStateHandle.getStateFlow<PostDto?>("post", null)
                }
                val createdPost = remember {
                    it.savedStateHandle.getStateFlow<PostDto?>("createdPost",null)
                }
                MainScreen(postFlow = postDto, createdPostDto = createdPost, nav = {route->
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
                    rootController.previousBackStackEntry?.savedStateHandle?.set<PostDto?>("createdPost",post)
                }, nav = {route->
                    rootController.navigate(route)
                })
            }
            composable<AppRoutes.AddPost>{
                AddPostScreen(pop = {post->
                    rootController.previousBackStackEntry?.savedStateHandle?.set("createdPost",post)
                    rootController.popBackStack()
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
