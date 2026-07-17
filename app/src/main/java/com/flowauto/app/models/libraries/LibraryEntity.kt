package com.flowauto.app.models.libraries

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "libraries")
data class LibraryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val type: String, // JAVASCRIPT, PYTHON, RACCORD, SELECTOR
    val content: String = "",
    val description: String = "",
    val tags: String = "", // comma-separated
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val isFavorite: Boolean = false,
    val version: String = "1.0.0"
)
