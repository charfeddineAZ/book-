package com.flowauto.app.utils

object Constants {
    const val BASE_URL = "https://api.example.com"
    const val BOOKS_ENDPOINT = "/books"
    const val USERS_ENDPOINT = "/users"
    const val SEARCH_ENDPOINT = "/search"
    
    // Timeouts
    const val CONNECTION_TIMEOUT = 30000L
    const val READ_TIMEOUT = 30000L
    const val WRITE_TIMEOUT = 30000L
    
    // Pagination
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_ITEMS_PER_PAGE = 100
}
