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
    val idUsuario: Long,
    val nombre: String,
    val correoElectronico: String
)

data class UsuarioDto(
    val id: String,
    val nombre: String,
    val email: String
)