package com.vishnu.secretmessage.presentation

import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vishnu.secretmessage.MainActivity
import com.vishnu.secretmessage.data.remote.Post
import com.vishnu.secretmessage.data.remote.PostsResponse
import com.vishnu.secretmessage.viewmodel.PostViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(postViewModel: PostViewModel, mainActivity: MainActivity) {

    val posts by postViewModel.posts.collectAsState(PostsResponse(emptyList()))
    // State to keep track of the selected post
    val selectedPost = remember { mutableStateOf<Post?>(null) }

    // State for swipe to refresh
    val isRefreshing by postViewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        modifier = Modifier.background(Color.LightGray),
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { postViewModel.refreshPosts() }
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {

            var isDialogVisible by remember { mutableStateOf(false) }
            var textValue by remember { mutableStateOf("") }

            // Function to handle opening the dialog
            fun openDialog() {
                isDialogVisible = true
            }

            // Function to handle dismissing the dialog
            fun dismissDialog() {
                isDialogVisible = false
            }
            Text(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.TopCenter),
                text = "Anonymous Space",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 24.sp,
                color = Color.Black
            )
            LazyColumn(modifier = Modifier.padding(top = 60.dp, bottom = 40.dp)) {
                items(posts.posts.reversed()) { post ->
                    // Display Post in a Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .clickable {

                            },
                        shape = RoundedCornerShape(6.dp),
                        onClick = {
                            selectedPost.value = post
                            openDialog()
                            Log.d(
                                "PostScreen",
                                "PostScreen, ${post.id}, ${post.content}, ${post.comments}, ${post.likes}"
                            )
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Post: ${post.content}",
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 20.sp
                        )

                        post.comments.forEach { comment ->
                            Text(
                                modifier = Modifier.padding(16.dp),
                                text = "Comment: ${comment.content}",
                                fontStyle = FontStyle.Normal,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(60.dp),
                onClick = {
                    selectedPost.value = null
                    openDialog()
                }) {
                Text(text = "Add Post")
            }

            // Dialog
            MyDialog(
                isDialogVisible = isDialogVisible,
                onDismiss = { dismissDialog() },
                onConfirm = { confirmedTextValue ->
                    if (selectedPost.value?.id == null) {
                        postViewModel.createPost(confirmedTextValue)
                        println("Added post: $confirmedTextValue")
                    } else {
                        postViewModel.addCommentToPost(selectedPost.value?.id!!, confirmedTextValue)
                        println("Added comment: $confirmedTextValue")
                    }
                    Log.d(
                        "PostScreen",
                        "PostScreen@@@, ${selectedPost.value?.id}, ${selectedPost.value?.content}, ${selectedPost.value?.comments}, ${selectedPost.value?.likes}"
                    )
                    postViewModel.refreshPosts()
                },
                textValue = textValue,
                onTextValueChange = { newTextValue -> textValue = newTextValue },
                postViewModel = postViewModel
            )
// We need to clear the text value otherwise var remembers it so that can be done in DisposableEffect scope.
            DisposableEffect(isDialogVisible) {
                onDispose {
                    textValue = ""
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Dialog code ends

        }
    }

    DisposableEffect(Unit) {
        WindowCompat.setDecorFitsSystemWindows(mainActivity.window, false)
        mainActivity.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        onDispose {
            WindowCompat.setDecorFitsSystemWindows(mainActivity.window, true)
            mainActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
}

@Composable
fun MyDialog(
    isDialogVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit, // Accepts a function as a parameter
    textValue: String,
    onTextValueChange: (String) -> Unit,
    postViewModel: PostViewModel
) {
    if (isDialogVisible) {
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                DialogContent(
                    textValue = textValue,
                    onTextValueChange = onTextValueChange,
                    onConfirm = { confirmedTextValue ->
                        if (confirmedTextValue.isNotBlank()) {
                            onConfirm(confirmedTextValue) // Call the provided onConfirm function
                        }
                        onDismiss()
                        postViewModel.refreshPosts()
                    },
                    onDismiss = onDismiss
                )
            }
        )
    }
}