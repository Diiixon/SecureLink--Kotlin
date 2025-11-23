package com.example.securelink.model

import com.google.gson.annotations.SerializedName

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