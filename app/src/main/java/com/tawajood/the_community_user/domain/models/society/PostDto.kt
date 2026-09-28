package com.tawajood.the_community_user.domain.models.society

data class PostDto(
    val id: Int?,
    val content: String?,
    val category: String?,
    val media : List<String?>?,
    val user:User?,
    val likesCount: Int?,
    val commentsCount: Int?,
    val comments : List<Comment?>?,
    val created_at: String?,
    val from: String?,
    val likeStatus: Boolean?

)
data class User(
    val id: Int?,
    val name: String?,
    val image: String?
)
data class Comment(
    val id: Int?,
    val comment: String?,
    val user: User?,
)