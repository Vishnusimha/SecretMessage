package com.vishnu.secretmessage.repository

import com.vishnu.secretmessage.data.remote.Comment
import com.vishnu.secretmessage.data.remote.LikesResponse
import com.vishnu.secretmessage.data.remote.PostService
import com.vishnu.secretmessage.data.remote.PostsResponse
import javax.inject.Inject

class PostRepository @Inject constructor(private val postService: PostService) {

    suspend fun createPost(content: String) {
        postService.createPost(mapOf("content" to content))
    }

    suspend fun addCommentToPost(postId: Int, content: String) {
        postService.addCommentToPost(postId, mapOf("content" to content))
    }

    suspend fun getCommentsForPost(postId: Int): List<Comment> {
        return postService.getCommentsForPost(postId)
    }

    suspend fun deleteCommentInPost(postId: Int, commentId: Int): Any {
        return postService.deleteComment(postId, commentId)
    }

    suspend fun likePost(postId: Int) {
        postService.likePost(postId)
    }

    suspend fun dislikePost(postId: Int) {
        postService.dislikePost(postId)
    }

    suspend fun getPostLikes(postId: Int): Int {
        val response: LikesResponse = postService.getPostLikes(postId)
        return response.likes
    }

    suspend fun getAllPosts(): PostsResponse {
        return postService.getAllPosts()
    }
}
