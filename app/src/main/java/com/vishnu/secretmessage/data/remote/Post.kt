package com.vishnu.secretmessage.data.remote

data class Post(
    val id: Int,
    val content: String,
    val likes: Int,
    val comments: List<Comment>
)
