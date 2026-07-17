package com.flowauto.app.models

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val rating: Float,
    val category: String,
    val description: String = "",
    val coverUrl: String = "",
    val year: Int = 2024
)
