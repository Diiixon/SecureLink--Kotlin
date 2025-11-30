package com.example.securelink.network

import com.example.securelink.model.ComparativaResponse
import com.example.securelink.model.DistribucionStats
import com.example.securelink.model.Report
import com.example.securelink.model.ResumenStats
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz dedicada exclusivamente a los endpoints del microservicio de estadísticas.
 */
interface StatsApiService {

    /**
     * Obtiene el resumen de todos los informes.
     *
     * @return Un mapa con los datos del resumen.
     */
    @GET("api/stats/resumen")
    suspend fun getResumen(): Response<Map<String, Any>>

    /**
     * Obtiene la distribución de todos los informes.
     *
     * @return Una lista de mapas con los datos de distribución.
     */
    @GET("api/stats/distribucion")
    suspend fun getDistribucion(): Response<List<Map<String, Any>>>

    /**
     * Obtiene los informes recientes.
     *
     * @param limit El número máximo de informes a obtener.
     * @return Una lista de informes.
     */
    @GET("api/stats/recientes")
    suspend fun getRecientes(@Query("limit") limit: Int = 5): Response<List<Report>>

    /**
     * Obtiene el resumen de los informes de un usuario.
     *
     * @param userId El ID del usuario.
     * @return Un objeto ResumenStats.
     */
    @GET("api/stats/usuario/{userId}/resumen")
    suspend fun getResumenUsuario(@Path("userId") userId: Long): Response<ResumenStats>

    /**
     * Obtiene la distribución de los informes de un usuario.
     *
     * @param userId El ID del usuario.
     * @return Una lista de objetos DistribucionStats.
     */
    @GET("api/stats/usuario/{userId}/distribucion")
    suspend fun getDistribucionUsuario(@Path("userId") userId: Long): Response<List<DistribucionStats>>

    /**
     * Obtiene la comparación entre las estadísticas del usuario y las globales.
     *
     * @param userId El ID del usuario.
     * @return Un objeto ComparativaResponse.
     */
    @GET("api/stats/usuario/{userId}/comparativa")
    suspend fun getComparativaUsuarioVsGlobal(@Path("userId") userId: Long): Response<ComparativaResponse>
}