package com.example.securelink.model

import com.google.gson.annotations.SerializedName

/**
 * Para /api/stats/usuario/{userId}/resumen
 *
 * @property total El número total de informes.
 * @property maliciosos El número de informes maliciosos.
 * @property seguros El número de informes seguros.
 * @property sospechosos El número de informes sospechosos.
 */
data class ResumenStats(
    @SerializedName("total") val total: Int = 0,
    @SerializedName("maliciosos") val maliciosos: Int = 0,
    @SerializedName("seguros") val seguros: Int = 0,
    @SerializedName("sospechosos") val sospechosos: Int = 0
)

/**
 * Para /api/stats/usuario/{userId}/distribucion
 *
 * @property estado El estado del informe.
 * @property cantidad El número de informes con ese estado.
 */
data class DistribucionStats(
    @SerializedName("estado") val estado: String,
    @SerializedName("cantidad") val cantidad: Int
)

/**
 * Para /api/stats/usuario/{userId}/comparativa
 * CORRECCIÓN: Se cambia de List a Map para que coincida con la respuesta de la API
 *
 * @property usuario Las estadísticas del usuario.
 * @property global Las estadísticas globales.
 */
data class ComparativaResponse(
    @SerializedName("usuario") val usuario: Map<String, Int>,
    @SerializedName("global") val global: Map<String, Int>
)