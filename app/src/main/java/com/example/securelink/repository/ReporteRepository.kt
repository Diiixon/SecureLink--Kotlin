package com.example.securelink.repository

import android.content.Context
import android.util.Log
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.Report
import com.example.securelink.network.RetrofitClient

/**
 * Estadísticas calculadas.
 *
 * @property totalReportes El número total de informes.
 * @property urlsSeguras El número de URL seguras.
 * @property urlsSospechosas El número de URL sospechosas.
 * @property urlsPeligrosas El número de URL peligrosas.
 */
data class EstadisticasCalculadas(
    val totalReportes: Int,
    val urlsSeguras: Int,
    val urlsSospechosas: Int,
    val urlsPeligrosas: Int
)

/**
 * Repositorio para manejar operaciones de datos relacionadas con informes.
 *
 * @param context El contexto de la aplicación.
 */
class ReportesRepository(private val context: Context) {

    // CORRECCIÓN: Apuntar al servicio de API de Análisis correcto
    private val apiService = RetrofitClient.analysisApiService
    private val sessionManager = SessionManager(context)

    /**
     * Obtiene los informes del usuario.
     *
     * @return Un objeto Result que contiene una lista de informes o una excepción.
     */
    suspend fun obtenerReportesUsuario(): Result<List<Report>> {
        return try {
            val token = sessionManager.getAuthToken()
            if (token.isNullOrEmpty()) {
                return Result.failure(Exception("No hay token de autenticación"))
            }

            Log.d("ReportesRepository", "Obteniendo reportes con token: Bearer $token")
            val response = apiService.obtenerHistorialAnalisis(
                token = "Bearer $token",
                contentType = "application/json"
            )

            when {
                response.isSuccessful && response.body() != null -> {
                    Log.d("ReportesRepository", "Reportes obtenidos: ${response.body()!!.size}")
                    Result.success(response.body()!!)
                }
                response.code() == 500 -> {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ReportesRepository", "Error 500 del backend: $errorBody")
                    Result.failure(Exception("Error interno del servidor. Revisa los logs del backend."))
                }
                else -> {
                    Log.e("ReportesRepository", "Error ${response.code()}: ${response.message()}")
                    Result.failure(Exception("Error al obtener reportes: ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            Log.e("ReportesRepository", "Excepción al obtener reportes: ${e.message}", e)
            Result.failure(e)
        }
    }

    /**
     * Calcula estadísticas a partir de una lista de informes.
     *
     * @param reportes La lista de informes.
     * @return Un objeto EstadisticasCalculadas.
     */
    fun calcularEstadisticas(reportes: List<Report>): EstadisticasCalculadas {
        var seguras = 0
        var sospechosas = 0
        var peligrosas = 0

        reportes.forEach { report ->
            when (report.peligro?.lowercase()) {
                "seguro" -> seguras++
                "sospechoso" -> sospechosas++
                "peligroso" -> peligrosas++
                else -> sospechosas++
            }
        }

        return EstadisticasCalculadas(
            totalReportes = reportes.size,
            urlsSeguras = seguras,
            urlsSospechosas = sospechosas,
            urlsPeligrosas = peligrosas
        )
    }
}