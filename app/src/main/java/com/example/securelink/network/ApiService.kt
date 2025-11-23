package com.example.securelink.network

import com.example.securelink.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("api/auth/login")
    suspend fun login(
        @Body credentials: Map<String, String>
    ): Response<LoginResponse>

    @POST("api/auth/register")
    suspend fun register(
        @Body userData: Map<String, String>
    ): Response<RegisterResponse>

    @GET("api/v1/reports")
    suspend fun obtenerHistorialAnalisis(
        @Header("Authorization") token: String,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<List<Report>>

    @POST("api/v1/analysis/scan-text")
    suspend fun analizarUrl(
        @Header("Authorization") token: String,
        @Body request: Map<String, String>
    ): Response<List<AnalisisResultado>>
}