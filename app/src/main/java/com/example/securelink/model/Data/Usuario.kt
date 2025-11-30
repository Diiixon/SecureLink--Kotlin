package com.example.securelink.model.Data

import java.util.Date

data class Usuario(
    val id: String = "",
    val nombre: String = "",
    val correo: String = "",
    val fechaRegistro: Date? = null
)