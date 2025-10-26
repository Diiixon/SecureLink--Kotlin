package com.example.securelink.model

// CORREGIDO: Propiedades en camelCase para seguir la convención de Kotlin.
data class UsuarioErrores(
    val nombreUsuario: String? = null,
    val correoElectronico: String? = null,
    val contrasena: String? = null,
    val contrasenaConfirmada: String? = null
)
