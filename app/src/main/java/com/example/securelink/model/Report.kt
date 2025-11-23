package com.example.securelink.model

import com.google.gson.annotations.SerializedName

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
