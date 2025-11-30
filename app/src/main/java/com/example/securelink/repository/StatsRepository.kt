package com.example.securelink.repository

import com.example.securelink.model.ComparativaResponse
import com.example.securelink.model.DistribucionStats
import com.example.securelink.model.ResumenStats
import com.example.securelink.model.StatItem
import com.example.securelink.network.StatsApiService // CORREGIDO: Dependencia correcta
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response

/**
 * Repositorio para manejar operaciones de datos relacionadas con estadísticas.
 * CORREGIDO: El constructor ahora pide una instancia de StatsApiService.
 *
 * @param statsApiService El servicio de API de estadísticas.
 */
class StatsRepository(private val statsApiService: StatsApiService) {

    /**
     * --- Funcionalidad para la pantalla de inicio ---
     * Obtiene las estadísticas para la pantalla de inicio.
     *
     * @return Un Flow que emite una lista de objetos StatItem.
     */
    fun getStats(): Flow<List<StatItem>> {
        val stats = listOf(
            StatItem(count = "1.2M", description = "Enlaces Analizados"),
            StatItem(count = "87.000+", description = "Amenazas Bloqueadas"),
            StatItem(count = "99.8%", description = "Tasa de Detección")
        )
        return flowOf(stats)
    }

    /**
     * --- Funcionalidad para la pantalla de estadísticas ---
     * CORREGIDO: Las llamadas ahora se hacen a statsApiService
     * Obtiene el resumen de los informes de un usuario.
     *
     * @param userId El ID del usuario.
     * @return Un objeto Response que contiene un objeto ResumenStats.
     */
    suspend fun getResumenUsuario(userId: Long): Response<ResumenStats> {
        return statsApiService.getResumenUsuario(userId)
    }

    /**
     * Obtiene la distribución de los informes de un usuario.
     *
     * @param userId El ID del usuario.
     * @return Un objeto Response que contiene una lista de objetos DistribucionStats.
     */
    suspend fun getDistribucionUsuario(userId: Long): Response<List<DistribucionStats>> {
        return statsApiService.getDistribucionUsuario(userId)
    }

    /**
     * Obtiene la comparación entre las estadísticas del usuario y las globales.
     *
     * @param userId El ID del usuario.
     * @return Un objeto Response que contiene un objeto ComparativaResponse.
     */
    suspend fun getComparativaUsuarioVsGlobal(userId: Long): Response<ComparativaResponse> {
        return statsApiService.getComparativaUsuarioVsGlobal(userId)
    }
}