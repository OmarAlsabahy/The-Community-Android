package com.tawajood.the_community_user.app.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.screens.home.HomeScreen
import com.tawajood.the_community_user.app.ui.screens.more.MoreScreen
import com.tawajood.the_community_user.app.ui.screens.society.PostImageScreen
import com.tawajood.the_community_user.app.ui.screens.society.SocietyHomeScreen
import com.tawajood.the_community_user.app.ui.shared.CustomBottomAppBar
import com.tawajood.the_community_user.domain.models.society.PostDto
import kotlinx.coroutines.flow.StateFlow

@Composable
fun MainScreen(nav:(AppRoutes?)-> Unit , postFlow: StateFlow<PostDto?>?,
               createdPostDto: StateFlow<PostDto?>?){
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.White,
        bottomBar = {
            CustomBottomAppBar(){route->
                if (route!=null){
                    navController.navigate(route)
                }
            }
        }) {innerPadding->
        NavHost(navController = navController , startDestination = AppRoutes.Home,
            modifier = Modifier.padding(innerPadding)) {
            composable<AppRoutes.Home>{
                HomeScreen(nav = nav)
            }
            composable<AppRoutes.SocietyHome>{
                SocietyHomeScreen(postFlow = postFlow,nav = {route->
                    nav(route)
                }, createdPostFlow = createdPostDto)
            }
            composable<AppRoutes.More>{
                MoreScreen(nav=nav)
            }
        }
    }
}