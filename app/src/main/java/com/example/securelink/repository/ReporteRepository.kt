package com.example.securelink.repository

import android.content.Context
import android.util.Log
import com.example.securelink.model.Data.SessionManager
import com.example.securelink.model.Report
import com.example.securelink.network.RetrofitClient

data class EstadisticasCalculadas(
    val totalReportes: Int,
    val urlsSeguras: Int,
    val urlsSospechosas: Int,
    val urlsPeligrosas: Int
)

class ReportesRepository(private val context: Context) {

    private val apiService = RetrofitClient.apiService
    private val sessionManager = SessionManager(context)

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
