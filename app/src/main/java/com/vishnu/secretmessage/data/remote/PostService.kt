package com.vishnu.secretmessage.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {
    @POST("post")
    suspend fun createPost(@Body requestBody: Map<String, String>): Any

    @POST("post/{post_id}/comment")
    suspend fun addCommentToPost(
        @Path("post_id") postId: Int,
        @Body requestBody: Map<String, String>
    ): Any

    @GET("post/{post_id}/comments")
    suspend fun getCommentsForPost(@Path("post_id") postId: Int): List<Comment>

    @POST("post/{post_id}/like")
    suspend fun likePost(@Path("post_id") postId: Int): Any

    @POST("post/{post_id}/dislike")
    suspend fun dislikePost(@Path("post_id") postId: Int): Any

    @GET("posts")
    suspend fun getAllPosts(): PostsResponse

    @GET("post/{post_id}/likes")
    suspend fun getPostLikes(@Path("post_id") postId: Int): LikesResponse
}