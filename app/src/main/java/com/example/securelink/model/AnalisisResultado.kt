package com.example.securelink.model

import com.google.gson.annotations.SerializedName

/**
 * Representa el resultado de un análisis de URL.
 *
 * @property url El enlace reportado.
 * @property peligro El nivel de peligro del enlace.
 * @property tipoAmenaza El tipo de amenaza.
 * @property imitaA La entidad a la que imita el enlace.
 * @property detalles Detalles adicionales del análisis.
 */
data class AnalisisResultado(
    @SerializedName("linkReportado")
    val url: String,

    val peligro: String,

    @SerializedName("tipoAmenaza")
    val tipoAmenaza: String?,

    @SerializedName("imitaA")
    val imitaA: String?,

    val detalles: Map<String, String>?
)