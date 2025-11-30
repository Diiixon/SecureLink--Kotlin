package com.example.securelink.model.Data

import java.util.Date

/**
 * Representa el resultado de un análisis de URL.
 *
 * @property id El identificador único del análisis.
 * @property url La URL que fue analizada.
 * @property resultado El resultado del análisis.
 * @property confianza El nivel de confianza del resultado del análisis.
 * @property esMaliciosa Si la URL es maliciosa o no.
 * @property fechaAnalisis La fecha del análisis.
 * @property userId El ID del usuario que realizó el análisis.
 */
data class Analisis(
    val id: String = "",
    val url: String = "",
    val resultado: String = "",
    val confianza: Double? = null,
    val esMaliciosa: Boolean? = null,
    val fechaAnalisis: String? = null,
    val userId: Long? = null
)