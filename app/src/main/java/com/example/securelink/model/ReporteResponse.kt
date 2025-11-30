package com.example.securelink.model

import com.google.gson.annotations.SerializedName

/**
 * Representa la respuesta de la API de informes.
 *
 * @property id El ID del informe.
 * @property url La URL reportada.
 * @property tipoAmenaza El tipo de amenaza.
 * @property peligro El nivel de peligro de la URL.
 * @property imitaA La entidad que imita la URL.
 * @property createdAt La fecha de creación del informe.
 */
data class ReporteResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("tipoAmenaza")
    val tipoAmenaza: String?,

    @SerializedName("peligro")
    val peligro: String?,

    @SerializedName("imitaA")
    val imitaA: String?,

    @SerializedName("createdAt")
    val createdAt: String?
)