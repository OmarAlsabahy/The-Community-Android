package com.tawajood.the_community_user.data.remote

class ApiPaths {
    companion object{
        const val login = "user/login"
        const val profile = "user/me"
        const val banners = "home/banners"
        const val announcement = "home/announcements"
        const val postsCategories = "post/categories"
        const val posts = "post"
        const val changePostLike = "post/like/{id}"
        const val postDetails = "post/show/{id}"
    }
}