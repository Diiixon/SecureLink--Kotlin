package com.example.securelink.model

import com.google.gson.annotations.SerializedName

/**
 * Representa la respuesta de la API de inicio de sesión.
 *
 * @property token El token de autenticación.
 */
data class LoginResponse(
    @SerializedName("token")
    val token: String
)

/**
 * Representa la respuesta de la API de registro.
 *
 * @property token El token de autenticación.
 * @property userId El ID del usuario registrado.
 * @property username El nombre de usuario del usuario registrado.
 */
data class RegisterResponse(
    val token: String,
    val userId: Int,
    val username: String?
)