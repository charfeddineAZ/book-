package com.flowauto.app.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.flowauto.app.models.Book

@Composable
fun HomeScreen(navController: NavHostController) {
    val books = listOf(
        Book(1, "كتاب الحياة", "محمد أحمد", 4.5f, "رواية"),
        Book(2, "الحب والعذاب", "فاطمة علي", 4.2f, "رومانسي"),
        Book(3, "رحلة المعرفة", "أحمد محمود", 4.8f, "تطوير ذاتي")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "أهلاً وسهلاً بك في فلو أوتو",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "الكتب المقترحة",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn {
            items(books) { book ->
                BookCard(book)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun BookCard(book: Book) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "للكاتب: ${book.author}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "التقييم: ${book.rating}⭐",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "النوع: ${book.category}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
