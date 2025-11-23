package com.example.securelink.model

import com.google.gson.annotations.SerializedName

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
