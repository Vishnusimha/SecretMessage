package com.vishnu.secretmessage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vishnu.secretmessage.presentation.LandingScreen
import com.vishnu.secretmessage.presentation.PostScreen
import com.vishnu.secretmessage.presentation.Screen
import com.vishnu.secretmessage.ui.theme.SecretMessageTheme
import com.vishnu.secretmessage.viewmodel.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val postViewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecretMessageTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    // Handle back button press
                    BackHandler {
                        if (navController.navigateUp()) {
                            // Handle the back navigation
                        }
                    }
// In the nav host we just add all the screens related to this activity and pass all req parameters into the screen
                    NavigationGraph(navController)
                }

            }
        }
    }

    @Composable
    private fun NavigationGraph(navController: NavHostController) {
        // In the nav host we just add all the screens related to this activity and pass all req parameters into the screen
        NavHost(
            navController = navController,
            startDestination = Screen.LandingScreen.route
        ) {
            composable(Screen.LandingScreen.route) {
                LandingScreen(navController)
            }
            composable(Screen.PostsScreen.route) {
                PostScreen(postViewModel, this@MainActivity)
            }
        }
    }
}
