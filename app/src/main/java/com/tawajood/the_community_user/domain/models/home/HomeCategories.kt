package com.tawajood.the_community_user.domain.models.home

import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes

sealed class HomeCategories(val title: String , val icon: Int,val route: AppRoutes?=null) {
    data class Permission(val name: String): HomeCategories(title = name , icon = R.drawable.permission_ic)
    data class Maintenance(val name: String): HomeCategories(title = name, icon = R.drawable.maintenance_ic,
        route = AppRoutes.Maintenance
    )
    data class Guide(val name: String): HomeCategories(title = name, icon = R.drawable.contact_ic,
        route = AppRoutes.Guide)
    data class Payments(val name: String): HomeCategories(title = name, icon = R.drawable.payments_ic)
    data class CommunitySettings(val name: String): HomeCategories(title = name, icon = R.drawable.community_services_ic)
    data class Community(val name: String): HomeCategories(title = name, icon = R.drawable.community_ic)
    data class Rents(val name: String): HomeCategories(title = name, icon = R.drawable.rents_ic)
    data class Help(val name: String): HomeCategories(title = name, icon = R.drawable.help_ic,
        route = AppRoutes.CustomerServices
    )
}