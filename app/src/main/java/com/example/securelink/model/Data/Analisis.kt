package com.example.securelink.model.Data

import java.util.Date

data class Analisis(
    val id: String = "",
    val url: String = "",
    val resultado: String = "",
    val confianza: Double? = null,
    val esMaliciosa: Boolean? = null,
    val fechaAnalisis: String? = null,
    val userId: Long? = null
)
