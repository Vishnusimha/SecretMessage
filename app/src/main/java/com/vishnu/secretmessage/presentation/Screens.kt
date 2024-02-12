package com.vishnu.secretmessage.presentation

sealed class Screen(val route: String) {
    object LandingScreen : Screen("landing_screen")
    object PostsScreen : Screen("posts_screen")
}
