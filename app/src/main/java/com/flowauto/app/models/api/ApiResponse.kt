package com.flowauto.app.models.api

data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T? = null,
    val message: String = "",
    val code: Int = 0
)
