package com.example.securelink.model

import com.google.gson.annotations.SerializedName

data class Distribucion(
    @SerializedName("estado")
    val estado: String?,

    @SerializedName("cantidad")
    val cantidad: Int?
)
