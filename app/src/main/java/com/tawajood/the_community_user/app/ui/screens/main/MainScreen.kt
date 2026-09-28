package com.tawajood.the_community_user.app.ui.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes
import com.tawajood.the_community_user.app.ui.screens.home.HomeScreen
import com.tawajood.the_community_user.app.ui.screens.society.SocietyHomeScreen
import com.tawajood.the_community_user.app.ui.shared.CustomBottomAppBar

@Composable
fun MainScreen(nav:(AppRoutes?)-> Unit){
    val navController = rememberNavController()
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.White,
        bottomBar = {
            CustomBottomAppBar(){route->
                if (route!=null){
                    navController.navigate(route)
                }
            }
        }) {innerPadding->
        NavHost(navController = navController , startDestination = AppRoutes.Home) {
            composable<AppRoutes.Home>{
                HomeScreen()
            }
            composable<AppRoutes.SocietyHome>{
                SocietyHomeScreen(nav = {route->
                    nav(route)
                })
            }
        }
    }
}