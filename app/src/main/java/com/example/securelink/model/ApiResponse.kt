package com.example.securelink.model

import com.google.gson.annotations.SerializedName

// Respuesta de LOGIN
data class LoginResponse(
    @SerializedName("token")
    val token: String
)

// Respuesta de REGISTRO
data class RegisterResponse(
    val token: String,
    val usuario: UsuarioResponse
)

