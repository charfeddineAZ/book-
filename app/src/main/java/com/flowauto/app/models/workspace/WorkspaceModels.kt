package com.flowauto.app.models.workspace

import java.time.LocalDateTime

enum class FileType {
    FOLDER, TEXT, JSON, CSV, JAVASCRIPT, PYTHON, MARKDOWN, IMAGE, PDF, VIDEO
}

data class FileItem(
    val name: String,
    val type: FileType,
    val path: String = "",
    val size: Long = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val modifiedAt: LocalDateTime = LocalDateTime.now(),
    val content: String = "",
    val isFavorite: Boolean = false,
    val isWatched: Boolean = false
)
