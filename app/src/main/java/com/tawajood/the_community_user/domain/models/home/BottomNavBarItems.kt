package com.tawajood.the_community_user.domain.models.home

import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes

sealed class BottomNavBarItems(val title: String , val icon: Int,val route : AppRoutes? = null) {
    data class Home(val name: String): BottomNavBarItems(title = name , icon = R.drawable.home_ic,
        AppRoutes.Home
    )
    data class Society(val name: String): BottomNavBarItems(title = name , icon = R.drawable.society_ic,
        route = AppRoutes.SocietyHome
    )
    data class Scan(val name: String): BottomNavBarItems(title = name , icon = R.drawable.scan_ic)
    data class Notification(val name: String): BottomNavBarItems(title = name , icon = R.drawable.notification_ic)
    data class Profile(val name: String): BottomNavBarItems(title = name , icon = R.drawable.profile_ic,
        route = AppRoutes.More
    )
}