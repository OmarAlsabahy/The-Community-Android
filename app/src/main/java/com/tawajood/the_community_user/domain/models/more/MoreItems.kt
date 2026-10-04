package com.tawajood.the_community_user.domain.models.more

import com.tawajood.the_community_user.R
import com.tawajood.the_community_user.app.language.Strings
import com.tawajood.the_community_user.app.ui.navigation.AppRoutes

sealed class MoreItems(val title: (Strings) -> String, val icon: Int, val route: AppRoutes? = null) {
    data object PersonalInfo : MoreItems(
        title = { it.personalInformation },
        icon = R.drawable.profile_ic,
        route = null,
    )

    data object PostsHistory : MoreItems(
        title = { it.postsHistory },
        icon = R.drawable.history_ic,
        route = null,
    )

    data object ComplaintsHistory : MoreItems(
        title = { it.complaintsHistory },
        icon = R.drawable.history_ic,
        route = AppRoutes.ComplaintHistory,
    )

    data object Favorites : MoreItems(
        title = { it.favorites },
        icon = R.drawable.fav_ic,
        route = null,
    )

    data object Language : MoreItems(
        title = { it.language },
        icon = R.drawable.lang_ic,
        route = null,
    )

    data object Notifications : MoreItems(
        title = { it.notifications },
        icon = R.drawable.notification_ic,
        route = null,
    )

    data object ContactUs : MoreItems(
        title = { it.contactUs },
        icon = R.drawable.phone_ic,
        route = null,
    )

    data object TermsAndConditions : MoreItems(
        title = { it.termsAndConditions },
        icon = R.drawable.streamline_justice_scale_1,
        route = null,
    )

    data object Logout : MoreItems(
        title = { it.logout },
        icon = R.drawable.solar_logout_2_bold,
        route = null,
    )
}
