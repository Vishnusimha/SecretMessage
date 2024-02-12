package com.vishnu.secretmessage.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun LandingScreen(navController: NavController) {
    Box() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Anonymous Chat",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Welcome to our anonymous chat app! \nHere, you can freely post and comment without revealing your identity. Express yourself openly and engage in discussions without any fear.",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(42.dp))
            Text(
                text = "Share and comment anonymously",
                color = Color.Black,
                textAlign = TextAlign.Right,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(42.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { navController.navigate(Screen.PostsScreen.route) }) {
                Text(text = "Get Started", color = Color.White)
            }
        }
    }
}
