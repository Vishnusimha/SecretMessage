package com.vishnu.secretmessage.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vishnu.secretmessage.data.remote.PostsResponse
import com.vishnu.secretmessage.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository: PostRepository) : ViewModel() {
    private val _posts = MutableStateFlow(PostsResponse(emptyList()))
    val posts: StateFlow<PostsResponse> get() = _posts.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> get() = _isRefreshing.asStateFlow()

    init {
        getAllPosts()
    }

    fun createPost(content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.createPost(content)
        }
    }

    fun addCommentToPost(postId: Int, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.addCommentToPost(postId, content)
        }
    }

    fun getCommentsForPost(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.getCommentsForPost(postId)
        }
    }

    fun likePost(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.likePost(postId)
        }
    }

    fun dislikePost(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.dislikePost(postId)
        }
    }

    fun getPostLikes(postId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            postRepository.getPostLikes(postId)
        }
    }

    private fun getAllPosts() {
        viewModelScope.launch {
            try {
                val fetchedPosts = postRepository.getAllPosts()
                _posts.value = fetchedPosts
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error fetching posts: $e")
            }
        }
    }

    fun refreshPosts() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                val fetchedPosts = postRepository.getAllPosts()
                _posts.value = fetchedPosts
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error fetching posts: $e")
            }
            _isRefreshing.value = false
        }
    }
}
