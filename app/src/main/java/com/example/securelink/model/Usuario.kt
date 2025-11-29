package com.example.securelink.model

data class RegistroRequest(
    val nombre: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class UsuarioResponse(
    val idUsuario: Long? = null,
    val nombre: String? = null,
    val correoElectronico: String? = null
)

data class UsuarioDto(
    val id: String,
    val nombre: String,
    val email: String
)