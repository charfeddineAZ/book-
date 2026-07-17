package com.flowauto.app.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val profilePictureUrl: String = "",
    val bio: String = "",
    val favoriteCategories: List<String> = emptyList(),
    val readBooks: List<Int> = emptyList()
)
