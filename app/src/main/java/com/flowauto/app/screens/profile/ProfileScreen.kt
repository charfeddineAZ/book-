package com.flowauto.app.screens.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "الملف الشخصي",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "اسم المستخدم: مستخدم",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "البريد الإلكتروني: user@example.com",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("تعديل الملف الشخصي")
        }
    }
}
