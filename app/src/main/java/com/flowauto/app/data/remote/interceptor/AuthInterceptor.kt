package com.flowauto.app.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(private val apiKey: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Authorization", "Bearer $apiKey")
            .header("Content-Type", "application/json")

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
