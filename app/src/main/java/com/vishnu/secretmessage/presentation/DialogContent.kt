package com.vishnu.secretmessage.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun DialogContent(
    textValue: String,
    onTextValueChange: (String) -> Unit,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Color.White)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        // Text input in the dialog
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = textValue,
            onValueChange = {
                onTextValueChange(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            label = {
                Text("Enter text")
            }
        )
        // Confirm button in the dialog
        Button(
            onClick = {
                // Handle the text value from the dialog
                onConfirm(textValue)
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("OK")
        }
    }
}


