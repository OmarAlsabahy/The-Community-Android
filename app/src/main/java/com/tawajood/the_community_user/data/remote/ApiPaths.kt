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
        const val comment = "post/comment"
        const val commentLike = "post/comment/like/{id}"
        const val save_remove_post = "post/save-remove"
        const val createPost = "post/store"
        const val forgetPassword = "user/forgot/password"
        const val verifyOtp = "user/verify/forgot/code"
        const val resetPassword = "user/reset/forgot/password"
        const val guideCategories = "guide/category"
        const val guides = "guide"
        const val guideSearch = "guide/search"
        const val customerServicesCategories = "customer-service/categories"
        const val addComplaint = "customer-service/store"
        const val complaintsHistory = "customer-service/history"
        const val maintenanceCategories = "maintenance/categories"
    }
}