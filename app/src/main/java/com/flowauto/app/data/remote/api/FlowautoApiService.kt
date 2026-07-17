package com.flowauto.app.data.remote.api

import com.flowauto.app.models.api.ApiResponse
import retrofit2.http.*

interface FlowautoApiService {
    @GET("workflows")
    suspend fun getWorkflows(): ApiResponse<List<String>>

    @GET("workflows/{id}")
    suspend fun getWorkflow(@Path("id") id: String): ApiResponse<String>

    @POST("workflows")
    suspend fun createWorkflow(@Body workflow: String): ApiResponse<String>

    @PUT("workflows/{id}")
    suspend fun updateWorkflow(@Path("id") id: String, @Body workflow: String): ApiResponse<String>

    @DELETE("workflows/{id}")
    suspend fun deleteWorkflow(@Path("id") id: String): ApiResponse<Unit>

    @POST("execute")
    suspend fun executeWorkflow(@Body payload: String): ApiResponse<String>

    @GET("logs/{scenarioId}")
    suspend fun getLogs(@Path("scenarioId") scenarioId: String): ApiResponse<List<String>>
}
