package com.flowauto.app.models.libraries

import java.time.LocalDateTime

enum class LibraryType {
    JAVASCRIPT, PYTHON, RACCORD, SELECTOR
}

data class LibraryItem(
    val id: String,
    val name: String,
    val type: LibraryType,
    val code: String? = null,
    val steps: List<String> = emptyList(),
    val selector: String? = null,
    val tags: List<String> = emptyList(),
    val description: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val version: String = "1.0.0",
    val isFavorite: Boolean = false,
    val testData: String = "",
    val rating: Float = 0f,
    val downloads: Int = 0
)
