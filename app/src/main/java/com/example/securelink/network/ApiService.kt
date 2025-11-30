package com.example.securelink.network

import com.example.securelink.model.AnalisisResultado
import com.example.securelink.model.Report
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Interfaz dedicada a los endpoints del microservicio de Análisis y Reportes.
 */
interface ApiService {

    /**
     * Obtiene el historial de análisis.
     *
     * @param token El token de autorización.
     * @param contentType El tipo de contenido de la solicitud.
     * @return Una lista de informes.
     */
    @GET("api/v1/reports")
    suspend fun obtenerHistorialAnalisis(
        @Header("Authorization") token: String,
        @Header("Content-Type") contentType: String = "application/json"
    ): Response<List<Report>>

    /**
     * Analiza una URL.
     *
     * @param token El token de autorización.
     * @param request El cuerpo de la solicitud que contiene la URL a analizar.
     * @return Una lista de resultados de análisis.
     */
    @POST("api/v1/analysis/scan-text")
    suspend fun analizarUrl(
        @Header("Authorization") token: String,
        @Body request: Map<String, String>
    ): Response<List<AnalisisResultado>>
}