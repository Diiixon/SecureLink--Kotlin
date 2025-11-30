package com.example.securelink.model

import com.google.gson.annotations.SerializedName

/**
 * Representa un informe de una URL.
 *
 * @property id El ID del informe.
 * @property url La URL reportada.
 * @property peligro El nivel de peligro de la URL ("seguro", "peligroso", "sospechoso").
 * @property tipoAmenaza El tipo de amenaza.
 * @property createdAt La fecha de creación del informe.
 * @property detalles Detalles adicionales del informe.
 * @property imitaA La entidad que imita la URL.
 * @property userId El ID del usuario que creó el informe.
 */
data class Report(
    @SerializedName("id")
    val id: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("peligro")
    val peligro: String?, // "seguro", "peligroso", "sospechoso"

    @SerializedName(value = "tipoAmenaza", alternate = ["tipo_amenaza"])
    val tipoAmenaza: String?,

    @SerializedName(value = "createdAt", alternate = ["created_at"])
    val createdAt: String?,

    @SerializedName("detalles")
    val detalles: String?,

    @SerializedName(value = "imitaA", alternate = ["imita_a"])
    val imitaA: String?,

    @SerializedName(value = "userId", alternate = ["user_id"])
    val userId: Int?
)