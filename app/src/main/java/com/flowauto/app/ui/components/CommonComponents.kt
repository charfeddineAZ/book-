package com.flowauto.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDialog(isLoading: Boolean, message: String = "جاري التحميل...") {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(androidx.compose.ui.Alignment.Center)
            )
            Text(
                text = message,
                modifier = Modifier
                    .align(androidx.compose.ui.Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ErrorDialog(error: String?, onDismiss: () -> Unit) {
    if (error != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("خطأ") },
            text = { Text(error) },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("موافق")
                }
            }
        )
    }
}

@Composable
fun SuccessSnackbar(message: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = message,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
