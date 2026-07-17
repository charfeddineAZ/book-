package com.flowauto.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowauto.app.models.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadBooks()
    }

    private fun loadBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // محاكاة تحميل الكتب من API
                val mockBooks = listOf(
                    Book(1, "كتاب الحياة", "محمد أحمد", 4.5f, "رواية"),
                    Book(2, "الحب والعذاب", "فاطمة علي", 4.2f, "رومانسي"),
                    Book(3, "رحلة المعرفة", "أحمد محمود", 4.8f, "تطوير ذاتي")
                )
                _books.value = mockBooks
            } finally {
                _isLoading.value = false
            }
        }
    }
}
